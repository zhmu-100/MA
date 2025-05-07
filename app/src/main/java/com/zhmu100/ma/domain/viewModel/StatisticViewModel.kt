package com.zhmu100.ma.domain.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.model.statistic.DailyStats
import com.zhmu100.ma.domain.model.statistic.NutritionDay
import com.zhmu100.ma.domain.model.statistic.WeeklyStatItem
import com.zhmu100.ma.domain.storage.TokenStorage
import com.zhmu100.ma.domain.utils.DateTimeUtils
import com.zhmu100.ma.domain.utils.DateTimeUtils.isInDay
import com.zhmu100.ma.domain.utils.DateTimeUtils.parseStringToLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class StatisticViewModel(
    private val trainingApi: TrainingApi,
    private val dietApi: DietApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    // Статистика за день (сегодня)
    private val _todayStats = MutableStateFlow<DailyStats?>(null)
    val todayStats = _todayStats.asStateFlow()

    // Статистика за неделю (7 дней ПН–ВС), изначально заполнены нулями
    private val initialDates = DateTimeUtils.getSevenDaysFrom(DateTimeUtils.getMondayOfThisWeek())
    private val _weeklySteps = MutableStateFlow<List<Pair<LocalDate, Int>>>(
        initialDates.map { Pair(it, 0) }
    )
    private val _weeklyDistance = MutableStateFlow<List<Pair<LocalDate, Double>>>(
        initialDates.map { Pair(it, 0.0) }
    )
    private val _weeklyNutrition = MutableStateFlow<List<Pair<LocalDate, NutritionDay>>>(
        initialDates.map {
            Pair(
                it,
                NutritionDay(
                    calories = 0,
                    protein = 0.0,
                    carbs = 0.0,
                    fats = 0.0
                )
            )
        }
    )

    val weeklySteps = _weeklySteps.asStateFlow()
    val weeklyDistance = _weeklyDistance.asStateFlow()
    val weeklyNutrition = _weeklyNutrition.asStateFlow()

    // Формирует общий список статистики за неделю
    private val _weeklyStats = combine(
        _weeklySteps,
        _weeklyDistance,
        _weeklyNutrition
    ) { stepsList, distanceList, nutritionList ->
        List(7) { i ->
            WeeklyStatItem(
                date = distanceList[i].first,
                steps = stepsList[i].second,
                distance = distanceList[i].second,
                calories = nutritionList[i].second.calories.toDouble(),
                protein = nutritionList[i].second.protein,
                carbs = nutritionList[i].second.carbs,
                fats = nutritionList[i].second.fats
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    /**
     * Получить статистику за определенную неделю (начиная с понедельника)
     */
    fun loadWeeklyStats(startDate: LocalDate = DateTimeUtils.getMondayOfThisWeek()) {
        val daysOfWeek = DateTimeUtils.getSevenDaysFrom(startDate)

        // Загрузка данных о тренировках
        viewModelScope.launch {
            runCatching {
                fetchTrainingStats(daysOfWeek)
                fetchMealStats(daysOfWeek)
            }
        }
    }

    /**
     * Загрузка данных по шагам и дистанции
     */
    private suspend fun fetchTrainingStats(days: List<LocalDate>) {
        viewModelScope.launch {
            runCatching {
                val dailySteps = mutableListOf<Pair<LocalDate, Int>>()
                val dailyDistance = mutableListOf<Pair<LocalDate, Double>>()
                val allWorkouts = trainingApi.listWorkouts(page = 1, pageSize = 20)

                for (day in days) {
                    val dayWorkouts = allWorkouts
                        .filter { it.userId == tokenStorage.getUserId() }
                        .filter { parseStringToLocalDate(it.date) == day }
                    val workouts = dayWorkouts.map { trainingApi.getWorkout(it.id) }

                    val totalSteps = workouts.sumOf { workout ->
                        workout.excercises.sumOf { it.steps ?: 0 }
                    }

                    val totalDistance = workouts.sumOf { workout ->
                        workout.excercises.sumOf { it.distance ?: 0 }
                    }

                    // Добавляем данные за день
                    dailySteps.add(Pair(day, totalSteps))
                    dailyDistance.add(Pair(day, totalDistance.toDouble()))
                }

                // Убедимся, что список содержит ровно 7 дней
                if (dailySteps.size < 7) {
                    for (day in days) {
                        if (!dailySteps.any { it.first == day }) {
                            dailySteps.add(Pair(day, 0))
                        }
                        if (!dailyDistance.any { it.first == day }) {
                            dailyDistance.add(Pair(day, 0.0))
                        }
                    }
                }

                // Сортируем по дате (на случай, если API вернул данные не по порядку)
                val sortedSteps = dailySteps.sortedBy { it.first }
                val sortedDistance = dailyDistance.sortedBy { it.first }

                _weeklySteps.value = sortedSteps
                _weeklyDistance.value = sortedDistance
            }.onFailure {
                Log.e("HTTP", it.toString())
            }
        }
    }

    /**
     * Загрузка питания за неделю
     */
    private suspend fun fetchMealStats(days: List<LocalDate>) {
        val nutritionByDay = mutableListOf<Pair<LocalDate, NutritionDay>>()
        val _day = DateTimeFormatter.ISO_LOCAL_DATE.format(days[0])
        val allMeals = dietApi.listMeals(_day, _day)

        for (day in days) {
            val meals = allMeals
                .filter { LocalDateTime.parse(it.date).toLocalDate() == day }

            var totalCalories = 0.0
            var totalProtein = 0.0
            var totalCarbs = 0.0
            var totalFats = 0.0

            meals.forEach { meal ->
                totalCalories += meal.foods.sumOf { it.calories }
                totalProtein += meal.foods.sumOf { it.protein }
                totalCarbs += meal.foods.sumOf { it.carbs }
                totalFats += meal.foods.sumOf { it.saturatedFats + it.transFats }
            }

            nutritionByDay.add(
                Pair(
                    day,
                    NutritionDay(
                        calories = totalCalories.roundToInt(),
                        protein = totalProtein,
                        carbs = totalCarbs,
                        fats = totalFats
                    )
                )
            )
        }

        _weeklyNutrition.value = nutritionByDay
        _todayStats.value = nutritionByDay.find { it.first == LocalDate.now() }?.second?.let {
            DailyStats(
                date = LocalDate.now().toString(),
                steps = dailyStepsForToday(),
                distance = dailyDistanceForToday(),
                calories = it.calories,
                protein = it.protein,
                carbs = it.carbs,
                fats = it.fats
            )
        }
    }

    private fun dailyStepsForToday(): Int = _weeklySteps.value
        .find { it.first == LocalDate.now() }
        ?.second ?: 0

    private fun dailyDistanceForToday(): Double = _weeklyDistance.value
        .find { it.first == LocalDate.now() }
        ?.second ?: 0.0


    /**
     * Получить статистику за сегодня
     */
    fun loadTodayStats() {
        viewModelScope.launch {
            runCatching {
                val today = LocalDate.now()
                val formatted = DateTimeFormatter.ISO_LOCAL_DATE.format(today)

                val allWorkouts = trainingApi.listWorkouts(0, 10)
                    .filter { it.userId == tokenStorage.getUserId() }
                    .filter { it.date.isInDay(today) }
                val workouts = allWorkouts.map { trainingApi.getWorkout(it.id) }

                Log.i("HTTP", workouts.toString())

                val steps = workouts.sumOf { workout ->
                    workout.excercises.sumOf { ex -> ex.steps ?: 0 }
                }

                val distance = workouts.sumOf { workout ->
                    workout.excercises.sumOf { ex -> ex.distance ?: 0 }
                }

                // Берём только приёмы пищи за сегодня
                val meals = dietApi.listMeals(formatted, formatted)
                    .filter { LocalDateTime.parse(it.date).toLocalDate() == today }

                val calories = meals.sumOf { it.foods.sumOf { food -> food.calories.toInt() } }
                val protein = meals.sumOf { it.foods.sumOf { food -> food.protein } }
                val carbs = meals.sumOf { it.foods.sumOf { food -> food.carbs } }
                val fats =
                    meals.sumOf { it.foods.sumOf { food -> food.saturatedFats + food.transFats } }

                _todayStats.value = DailyStats(
                    date = today.toString(),
                    steps = steps,
                    distance = distance.toDouble(),
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fats = fats
                )
            }.onFailure {
                Log.e("HTTP", it.toString())
            }
        }
    }
}
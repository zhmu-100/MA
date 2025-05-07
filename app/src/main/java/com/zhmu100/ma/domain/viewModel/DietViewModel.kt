package com.zhmu100.ma.domain.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.model.diet.DailyStats
import com.zhmu100.ma.domain.model.diet.Food
import com.zhmu100.ma.domain.model.diet.Meal
import com.zhmu100.ma.domain.model.diet.MealRequest
import com.zhmu100.ma.domain.model.diet.MealType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * ViewModel для работы с данными о питании.
 *
 * Отвечает за:
 * - Загрузку списка продуктов
 * - Управление приемами пищи за текущий день
 * - Подсчет суммарных БЖУ и калорий за день
 * - Хранение информации о том, были ли приемы пищи за каждый день недели
 */
class DietViewModel(
    private val dietApi: DietApi
) : ViewModel() {

    /**
     * Список всех доступных продуктов.
     * Используется для отображения в поиске или выборе еды.
     */
    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods

    /**
     * Приемы пищи, сгруппированные по типу (`BREAKFAST`, `LUNCH` и т.д.)
     * Используется для отображения разделов на экране дня.
     */
    private val _mealsByTypeToday = mutableMapOf<MealType, List<Meal>>()
    val mealsByTypeToday: Map<MealType, List<Meal>> get() = _mealsByTypeToday

    /**
     * Текущая статистика потребления за сегодня.
     * Может использоваться в UI для отображения прогресса или графиков.
     */
    private val _dailyStats = mutableStateOf(DailyStats())
    val dailyStats: State<DailyStats> = _dailyStats

    /**
     * Статус дней недели: был ли добавлен хотя бы один прием пищи за день.
     * Используется для отображения "календаря активности" (зеленые/белые кружки).
     */
    private val _weekMealsStatus = mutableStateOf<Map<LocalDate, Boolean>>(emptyMap())
    val weekMealsStatus: State<Map<LocalDate, Boolean>> = _weekMealsStatus

    // --- Выбранный продукт для создания приема пищи ---
    private val _selectedFood = mutableStateOf<Food?>(null)
    val selectedFood: State<Food?> get() = _selectedFood
    // --- Выбранный тип приема пищи ---
    private val _selectedMealType = mutableStateOf<MealType>(MealType.MEAL_TYPE_BREAKFAST)
    val selectedMealType: State<MealType> get() = _selectedMealType
    // --- Параметры порции ---
    private val _portionAmount = mutableStateOf("100")
    val portionAmount: State<String> get() = _portionAmount
    private val _portionUnit = mutableStateOf("г")
    val portionUnit: State<String> get() = _portionUnit
    // --- Сохранение выбранного продукта ---
    fun selectFoodForMeal(food: Food) {
        _selectedFood.value = food
    }
    // --- Установка типа приема пищи ---
    fun selectMealType(type: MealType) {
        _selectedMealType.value = type
    }
    // --- Обновление количества ---
    fun updatePortionAmount(value: String) {
        _portionAmount.value = value
    }
    // --- Обновление единицы измерения ---
    fun updatePortionUnit(value: String) {
        _portionUnit.value = value
    }

    init {
        loadFoods()
    }

    fun refreshData() {
        refreshDataForToday()
        refreshWeekStatus()
    }

    /**
     * Загружает список всех продуктов из API и сохраняет его локально.
     */
    private fun loadFoods() {
        viewModelScope.launch {
            val response = dietApi.listFoods()
            _foods.emit(response)
        }
    }

    /**
     * Обновляет данные о приемах пищи за сегодня.
     * Также пересчитывает общую статистику по калориям и БЖУ.
     */
    private fun refreshDataForToday() {
        val today = LocalDate.now()

        viewModelScope.launch {
            val response = dietApi.listMeals(today.toString(), today.toString())

            // Очищаем старые данные и заполняем новые по типам приемов
            _mealsByTypeToday.clear()
            MealType.entries.forEach { type ->
                if (type != MealType.MEAL_TYPE_UNSPECIFIED) {
                    _mealsByTypeToday[type] = response.filter { it.mealType == type }
                }
            }

            // Суммируем показатели
            val stats = DailyStats()
            response.flatMap { it.foods }.forEach { food ->
                stats.calories += food.calories
                stats.protein += food.protein
                stats.carbs += food.carbs
                stats.fats += food.saturatedFats + food.transFats
            }

            _dailyStats.value = stats
        }
    }

    /**
     * Проверяет наличие приемов пищи за каждый день текущей недели (Пн-Вс).
     * Результат сохраняется в [weekMealsStatus], где ключ – дата, значение – есть ли запись.
     */
    private fun refreshWeekStatus() {
        val now = Instant.now()
        val zone = ZoneId.systemDefault()

        val startOfWeek = now.atZone(zone).toLocalDate().with(DayOfWeek.MONDAY)
        val datesThisWeek = (0..6).map { startOfWeek.plusDays(it.toLong()) }

        viewModelScope.launch {
            val response = dietApi.listMeals(startOfWeek.toString(), startOfWeek.toString())
            _weekMealsStatus.value = datesThisWeek.associateWith { date ->
                response.filter { LocalDateTime.parse(it.date).toLocalDate() == date }.isNotEmpty()
            }
        }
    }

    /**
     * Добавляет новый прием пищи через API.
     *
     * @param meal Объект приема пищи
     * @param onSuccess Вызывается при успешном добавлении
     * @param onError Вызывается при ошибке, передается исключение
     */
    fun addMeal(meal: MealRequest, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            runCatching {
                dietApi.createMeal(meal)
            }.onSuccess {
                refreshDataForToday()
                refreshWeekStatus()
                onSuccess()
            }.onFailure {
                onError(it)
            }
        }
    }

    /**
     * Ищет продукт по идентификатору в уже загруженном списке продуктов.
     *
     * @param id Идентификатор продукта
     * @return Найденный [Food] или null, если не найден
     */
    fun findFoodById(id: String): Food? {
        return _foods.value.find { it.id == id }
    }
}
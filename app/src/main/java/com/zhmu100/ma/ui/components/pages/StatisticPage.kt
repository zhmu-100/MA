package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.viewModel.StatisticViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.CategoryButton
import com.zhmu100.ma.ui.components.charts.DonutNutrients
import com.zhmu100.ma.ui.components.charts.StackableBarChart
import com.zhmu100.ma.ui.data.NutrientsData
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.caloriesColor
import com.zhmu100.ma.ui.theme.carbsColor
import com.zhmu100.ma.ui.theme.fatsColor
import com.zhmu100.ma.ui.theme.proteinsColor
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StatisticPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: StatisticViewModel = koinViewModel()
) {
    val currentCategory = remember { mutableStateOf("Питание") }
    val selectedWeek = remember { mutableStateOf(WeekUtils.getThisWeekRange()) }

    // Загрузка данных при изменении недели или категории
    LaunchedEffect(selectedWeek.value) {
        viewModel.loadWeeklyStats(selectedWeek.value.startDate)
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
                .fillMaxSize()
        ) {
            // Кнопки навигации
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                BackButton(text = "Назад", onClick = { navController?.navigate(ProfileScreen) })
                Spacer(modifier.width(16.dp))
                CategoryButton("Питание",
                    isActive = currentCategory.value == "Питание",
                    iconResource = R.drawable.coffee,
                    onClick = { currentCategory.value = "Питание" })
                Spacer(modifier.width(8.dp))
                CategoryButton("Шаги",
                    isActive = currentCategory.value == "Шаги",
                    iconResource = R.drawable.bolt,
                    onClick = { currentCategory.value = "Шаги" })
                Spacer(modifier.width(8.dp))
                CategoryButton("Дистанция",
                    isActive = currentCategory.value == "Дистанция",
                    iconResource = R.drawable.run,
                    onClick = { currentCategory.value = "Дистанция" })
            }

            // Стрелки для переключения недель
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Previous week",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            selectedWeek.value =
                                WeekUtils.getPreviousWeek(selectedWeek.value.startDate)
                        }
                )
                Text(
                    "${selectedWeek.value.startDate.format(WeekUtils.weekFormat)} - ${
                        selectedWeek.value.endDate.format(
                            WeekUtils.weekFormat
                        )
                    }"
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Next week",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            selectedWeek.value =
                                WeekUtils.getNextWeek(selectedWeek.value.startDate)
                        }
                )
            }

            // Отображение графиков в зависимости от выбранной категории
            when (currentCategory.value) {
                "Питание" -> NutritionCategory(viewModel, selectedWeek.value)
                "Шаги" -> StepsCategory(viewModel, selectedWeek.value)
                "Дистанция" -> DistanceCategory(viewModel, selectedWeek.value)
            }
        }
    }
}

@Composable
private fun NutritionCategory(viewModel: StatisticViewModel, week: WeekUtils.WeekRange) {
    val weeklyNutrition by viewModel.weeklyNutrition.collectAsState()

    val nutrientsData = remember(weeklyNutrition) {
        weeklyNutrition.map { (_, nutritionDay) ->
            NutrientsData(
                proteins = nutritionDay.protein.toFloat(),
                carbs = nutritionDay.carbs.toFloat(),
                fats = nutritionDay.fats.toFloat(),
                calories = nutritionDay.calories.toFloat()
            )
        }
    }

    val nutritionData = nutrientsData.map {
        listOf(it.proteins, it.carbs, it.fats, it.calories)
    }
    Text(
        text = "Статистика за неделю",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    StackableBarChart(
        nutritionData,
        labels = listOf("Белки", "Углеводы", "Жиры", "Калории"),
        colors = listOf(proteinsColor, carbsColor, fatsColor, caloriesColor)
    )

    // Сегодняшняя дата
    val todayIndex = remember(week) {
        LocalDate.now().dayOfWeek.ordinal
    }

    val todayNutrient = nutrientsData.getOrElse(todayIndex) {
        NutrientsData(0f, 0f, 0f, 0f)
    }
    Text(
        text = "Статистика за сегодня",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    DonutNutrients(todayNutrient)
}

@Composable
private fun StepsCategory(viewModel: StatisticViewModel, week: WeekUtils.WeekRange) {
    val weeklySteps by viewModel.weeklySteps.collectAsState()

    val stepsData = remember(weeklySteps) {
        weeklySteps.map { (_, steps) ->
            listOf(steps.toFloat())
        }
    }
    Text(
        text = "Статистика за неделю",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    StackableBarChart(stepsData,
        labels = listOf("Шаги"),
        colors = listOf(MaterialTheme.colorScheme.primary))
}

@Composable
private fun DistanceCategory(viewModel: StatisticViewModel, week: WeekUtils.WeekRange) {
    val weeklyDistance by viewModel.weeklyDistance.collectAsState()

    val distanceData = remember(weeklyDistance) {
        weeklyDistance.map { (_, distance) ->
            listOf(distance.toFloat())
        }
    }
    Text(
        text = "Статистика за неделю",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    StackableBarChart(
        data = distanceData,
        labels = listOf("Дистанция (км)"),
        colors = listOf(MaterialTheme.colorScheme.secondary)
    )
}

// Вспомогательный класс для хранения диапазона недели
private object WeekUtils {
    data class WeekRange(val startDate: LocalDate, val endDate: LocalDate)

    fun getMondayOfThisWeek(): LocalDate = LocalDate.now().with(DayOfWeek.MONDAY)

    fun getThisWeekRange(): WeekRange {
        val start = getMondayOfThisWeek()
        return WeekRange(start, start.plusDays(6))
    }

    fun getPreviousWeek(startDate: LocalDate): WeekRange =
        WeekRange(startDate.minusWeeks(1), startDate.minusDays(1))

    fun getNextWeek(startDate: LocalDate): WeekRange =
        WeekRange(startDate.plusWeeks(1), startDate.plusDays(13))

    val weekFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
}

@Serializable
object StatisticsScreen

@Preview(showBackground = true)
@Composable
private fun StatisticPagePreview() {
    MATheme {
        StatisticPage()
    }
}
package com.zhmu100.ma.ui.components.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.diet.MealType
import com.zhmu100.ma.domain.viewModel.DietViewModel
import com.zhmu100.ma.ui.components.buttons.*
import com.zhmu100.ma.ui.components.food.FoodRecommendationCard
import com.zhmu100.ma.ui.components.food.MealEntrySwitcher
import com.zhmu100.ma.ui.components.food.NutrientSummaryRow
import com.zhmu100.ma.ui.components.food.NutritionWeekIndicator
import com.zhmu100.ma.ui.data.FoodItem
import com.zhmu100.ma.ui.data.Recommendation
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun FoodPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DietViewModel = koinViewModel()
) {
    val mealsByTypeToday = viewModel.mealsByTypeToday
    val dailyStats by viewModel.dailyStats
    val weekMealsStatus by viewModel.weekMealsStatus
    val allFoods by viewModel.foods.collectAsStateWithLifecycle()

    val filledDays by remember(weekMealsStatus) {
        derivedStateOf {
            weekMealsStatus.filterValues { it }.keys.map { date ->
                (date.dayOfWeek.value - 1) % 7
            }
        }
    }

    val currentDayIndex by remember {
        derivedStateOf {
            (LocalDate.now().dayOfWeek.value - 1) % 7
        }
    }

    val breakfastItems = remember(mealsByTypeToday[MealType.MEAL_TYPE_BREAKFAST]) {
        mealsByTypeToday[MealType.MEAL_TYPE_BREAKFAST]?.flatMap { it.foods } ?: emptyList()
    }
    val lunchItems = remember(mealsByTypeToday[MealType.MEAL_TYPE_LUNCH]) {
        mealsByTypeToday[MealType.MEAL_TYPE_LUNCH]?.flatMap { it.foods } ?: emptyList()
    }
    val dinnerItems = remember(mealsByTypeToday[MealType.MEAL_TYPE_DINNER]) {
        mealsByTypeToday[MealType.MEAL_TYPE_DINNER]?.flatMap { it.foods } ?: emptyList()
    }
    val snackItems = remember(mealsByTypeToday[MealType.MEAL_TYPE_SNACK]) {
        mealsByTypeToday[MealType.MEAL_TYPE_SNACK]?.flatMap { it.foods } ?: emptyList()
    }

    Log.i("DIET", breakfastItems.toString())

    val recommendations = listOf(
        Recommendation(1, "Как вкусно готовить ?", "25 марта 2025", "", "https://example.com/1"),
        Recommendation(2, "Полезные советы", "24 марта 2025", "", "https://example.com/2")
    )

    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    BasePage(
        true,
        navIndex = 2,
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        navController = navController
    ) { baseModifier ->
        Column(
            modifier = baseModifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Сегодня",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            )
            NutritionWeekIndicator(
                filledDays = filledDays,
                currentDayIndex = currentDayIndex,
            )
            FoodRecommendationCard(recommendations)
            Spacer(modifier = Modifier.height(24.dp))
            BigIconButton(
                text = "Статистика",
                drawableResId = R.drawable.bars,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { navController?.navigate(StatisticsScreen) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            NutrientSummaryRow(
                fat = dailyStats.fats.toInt(),
                carbs = dailyStats.carbs.toInt(),
                protein = dailyStats.protein.toInt(),
                calories = dailyStats.calories.toInt()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MealEntrySwitcher(
                "Завтрак",
                R.drawable.breakfast,
                breakfastItems.map { FoodItem.fromFood(it, allFoods) }) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))
            MealEntrySwitcher("Обед", R.drawable.lunch, lunchItems.map { FoodItem.fromFood(it, allFoods) }) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))
            MealEntrySwitcher(
                "Ужин",
                R.drawable.dinner,
                dinnerItems.map { FoodItem.fromFood(it, allFoods) }) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))
            MealEntrySwitcher(
                "Перекус",
                R.drawable.snack,
                snackItems.map { FoodItem.fromFood(it, allFoods) }) {
                navController?.navigate(FoodAddScreen)
            }
        }
    }
}


@Serializable
object FoodScreen

@Preview(showBackground = true)
@Composable
private fun FoodPagePreview() {
    MATheme {
        FoodPage()
    }
}

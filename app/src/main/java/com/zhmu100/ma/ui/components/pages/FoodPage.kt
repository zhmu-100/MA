package com.zhmu100.ma.ui.components.pages

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
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.*
import com.zhmu100.ma.ui.components.food.FoodRecommendationCard
import com.zhmu100.ma.ui.components.food.MealEntrySwitcher
import com.zhmu100.ma.ui.components.food.NutrientSummaryRow
import com.zhmu100.ma.ui.components.food.NutritionWeekIndicator
import com.zhmu100.ma.ui.data.FoodItem
import com.zhmu100.ma.ui.data.Recommendation
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Composable
fun FoodPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    val filledDays = listOf(0, 1, 2)
    val currentDayIndex = (LocalDate.now().dayOfWeek.value - 1) % 7
    val recommendations = listOf(
        Recommendation(1, "Как вкусно готовить ?", "25 марта 2025", "", "https://example.com/1"),
        Recommendation(2, "Полезные советы", "24 марта 2025", "", "https://example.com/2")
    )

    val breakfast = remember { mutableStateListOf<FoodItem>() }
    val lunch = remember { mutableStateListOf<FoodItem>() }
    val dinner = remember { mutableStateListOf<FoodItem>() }
    val snack = remember { mutableStateListOf<FoodItem>() }

    val allItems = breakfast + lunch + dinner + snack

    val totalFat = allItems.sumOf { it.fat }
    val totalCarbs = allItems.sumOf { it.carbs }
    val totalProtein = allItems.sumOf { it.protein }
    val totalCalories = allItems.sumOf { it.calories }

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
                fat = totalFat,
                carbs = totalCarbs,
                protein = totalProtein,
                calories = totalCalories
            )

            Spacer(modifier = Modifier.height(16.dp))

            MealEntrySwitcher("Завтрак", R.drawable.breakfast, breakfast) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))

            MealEntrySwitcher("Обед", R.drawable.lunch, lunch) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))

            MealEntrySwitcher("Ужин", R.drawable.dinner, dinner) {
                navController?.navigate(FoodAddScreen)
            }
            Spacer(modifier = Modifier.height(16.dp))

            MealEntrySwitcher("Перекус", R.drawable.snack, snack) {
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

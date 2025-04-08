package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.WeekDots
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.CategoryButton
import com.zhmu100.ma.ui.components.charts.DonutNutrients
import com.zhmu100.ma.ui.components.charts.StackableNutrients
import com.zhmu100.ma.ui.data.NutrientsData
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun StatisticPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                BackButton(
                    text = "Назад",
                    onClick = { navController?.navigate(ProfileScreen) }
                )
                Spacer(modifier.width(16.dp))
                CategoryButton("Общая", isActive = true, iconResource = R.drawable.eye)
                Spacer(modifier.width(8.dp))
                CategoryButton("Рацион", isActive = false, iconResource = R.drawable.coffee)
                Spacer(modifier.width(8.dp))
                CategoryButton("Активность", isActive = false, iconResource = R.drawable.bolt)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    "left",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { })
                Text("14.02.2025 - 21.02.2025")
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    "right",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { })
            }

            val nutritionData = listOf(
                NutrientsData(100f, 30f, 30f, 100f),
                NutrientsData(40f, 50f, 30f, 20f),
                NutrientsData(30f, 10f, 20f, 20f),
                NutrientsData(30f, 10f, 20f, 20f),
                NutrientsData(30f, 10f, 20f, 20f),
                NutrientsData(30f, 10f, 20f, 20f),
                NutrientsData(30f, 10f, 20f, 20f),
            )
            StackableNutrients(nutritionData)
            Row {
                Spacer(modifier = Modifier.width(40.dp))
                WeekDots(3, modifier = Modifier.width(260.dp))
            }
            val nutritionData2 = NutrientsData(100f, 30f, 30f, 100f)
            DonutNutrients(nutritionData2)
        }
    }
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
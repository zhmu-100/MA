package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Отображает сводную строку с показателями нутриентов: жиры, углеводы, белки и калории.
 *
 * Используется, например, в карточках приёмов пищи для отображения суммарных значений по всем добавленным продуктам.
 *
 * @param fat Общее количество жиров, в граммах.
 * @param carbs Общее количество углеводов, в граммах.
 * @param protein Общее количество белков, в граммах.
 * @param calories Общее количество калорий.
 */

@Composable
fun NutrientSummaryRow(
    fat: Int,
    carbs: Int,
    protein: Int,
    calories: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Жиры", "Углев", "Белк", "Калории").forEachIndexed { index, label ->
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = if (label == "Калории") FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(fat, carbs, protein, calories).forEachIndexed { index, value ->
                Text(
                    text = value.toString(),
                    fontSize = 14.sp,
                    fontWeight = if (index == 3) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutrientSummaryRowPreview() {
    MATheme {
        NutrientSummaryRow(
            fat = 12,
            carbs = 20,
            protein = 10,
            calories = 250
        )
    }
}

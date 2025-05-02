package com.zhmu100.ma.ui.components.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Индикатор заполняемости рациона на неделю.
 *
 * Показывает визуально, в какие дни рацион был заполнен (primary),
 * какой день текущий (tertiary), а какие — ещё нет (пустые с границей primary).
 *
 * @param filledDays список индексов [0..6], где рацион был заполнен (0 = Пн, 6 = Вс)
 * @param currentDayIndex индекс текущего дня [0..6]
 */
@Composable
fun NutritionWeekIndicator(
    filledDays: List<Int>,
    currentDayIndex: Int
) {
    val days = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, day ->
            val isFilled = filledDays.contains(index)
            val isToday = index == currentDayIndex

            val backgroundColor = when {
                isToday -> MaterialTheme.colorScheme.tertiary
                isFilled -> MaterialTheme.colorScheme.primary
                else -> Color.Transparent
            }

            val borderColor = if (!isFilled && !isToday) MaterialTheme.colorScheme.primary else Color.Transparent

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color = backgroundColor, shape = CircleShape)
                        .border(
                            width = if (borderColor != Color.Transparent) 1.dp else 0.dp,
                            color = borderColor,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NutritionWeekIndicatorPreview() {
    MATheme {
        NutritionWeekIndicator(
            filledDays = listOf(0, 1, 2),
            currentDayIndex = 3
        )
    }
}

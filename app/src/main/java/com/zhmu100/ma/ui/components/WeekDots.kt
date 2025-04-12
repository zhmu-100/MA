package com.zhmu100.ma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент визуализации недели в виде точек с днями недели.
 *
 * @param currentDayIndex Индекс текущего дня недели (0-6, где 0=Пн)
 * @param modifier Модификатор для настройки внешнего вида
 */
@Composable
fun WeekDots(
    currentDayIndex: Int,
    modifier: Modifier = Modifier
) {
    val days = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    val activeColor = MaterialTheme.colorScheme.inversePrimary
    val currentColor = MaterialTheme.colorScheme.tertiary
    val inactiveColor = MaterialTheme.colorScheme.background

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEachIndexed { index, day ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(
                            color = when {
                                index < currentDayIndex -> activeColor
                                index == currentDayIndex -> currentColor
                                else -> inactiveColor
                            },
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Black,
                            shape = CircleShape
                        )
                )
                Text(text = day)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekDotsPreview() {
    MATheme {
        WeekDots(3)
    }
}
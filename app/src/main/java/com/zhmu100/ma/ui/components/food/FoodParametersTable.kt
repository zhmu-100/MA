package com.zhmu100.ma.ui.components.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.data.Mineral
import com.zhmu100.ma.ui.data.Vitamin
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент отображения подробной таблицы нутриентов и микроэлементов продукта.
 *
 * Содержит строки с основными нутриентами (калории, углеводы, белки, жиры, клетчатка, сахар),
 * а также списки витаминов и минералов, если они предоставлены.
 *
 * @param calories Количество калорий в продукте (в ккал)
 * @param protein Количество белков (в граммах)
 * @param carbs Количество углеводов (в граммах)
 * @param saturatedFats Количество насыщенных жиров (в граммах)
 * @param transFats Количество трансжиров (в граммах)
 * @param fiber Количество клетчатки (в граммах)
 * @param sugar Количество сахара (в граммах)
 * @param vitamins Список витаминов (название, количество, единица измерения)
 * @param minerals Список минералов (название, количество, единица измерения)
 *
 * Использует стили из текущей темы (MaterialTheme) для адаптации к светлой и тёмной теме.
 *
 * Пример использования:
 * ```
 * FoodParametersTable(
 *   calories = 120.0,
 *   protein = 10.0,
 *   carbs = 5.0,
 *   saturatedFats = 1.0,
 *   transFats = 0.0,
 *   fiber = 2.0,
 *   sugar = 3.0,
 *   vitamins = listOf(Vitamin("C", 60.0, "mg")),
 *   minerals = listOf(Mineral("Ca", 100.0, "mg"))
 * )
 * ```
 */
@Composable
fun FoodParametersTable(
    calories: Double,
    protein: Double,
    carbs: Double,
    saturatedFats: Double,
    transFats: Double,
    fiber: Double,
    sugar: Double,
    vitamins: List<Vitamin>,
    minerals: List<Mineral>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        FoodParamRow("Калории", "${calories.toInt()} ккал", "Жир", "${saturatedFats} г")
        FoodParamRow("Углев", "${carbs} г", "Белок", "${protein} г")
        FoodParamRow("Насыщ. жиры", "${saturatedFats} г", "Транс. жиры", "${transFats} г")
        FoodParamRow("Клетчатка", "${fiber} г", "Сахар", "${sugar} г")

        if (vitamins.isNotEmpty()) {
            Text(
                text = "Витамины",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            vitamins.forEach {
                InfoRow(it.name, "${it.amount} ${it.unit}")
            }
        }

        if (minerals.isNotEmpty()) {
            Text(
                text = "Минералы",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            minerals.forEach {
                InfoRow(it.name, "${it.amount} ${it.unit}")
            }
        }
    }
}

@Composable
private fun FoodParamRow(
    label1: String,
    value1: String,
    label2: String,
    value2: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 2.dp)
            .border(0.5.dp, MaterialTheme.colorScheme.background)
    ) {
        TableCell(label = label1, value = value1, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        )
        TableCell(label = label2, value = value2, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun TableCell(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(0.5.dp, MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FoodParametersTablePreview() {
    MATheme {
        FoodParametersTable(
            calories = 165.0,
            protein = 31.0,
            carbs = 0.0,
            saturatedFats = 1.2,
            transFats = 0.0,
            fiber = 0.0,
            sugar = 0.0,
            vitamins = listOf(
                Vitamin("D3", 12.0, " ml")
            ),
            minerals = listOf(
                Mineral("Омега", 13.0, " л")
            )
        )
    }
}

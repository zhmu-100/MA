package com.zhmu100.ma.ui.components.food

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.data.FoodItem
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент, отображающий либо кнопку добавления еды, либо карточку с продуктами,
 * в зависимости от наличия элементов [items].
 *
 * @param title Название приема пищи.
 * @param iconRes Ресурс иконки приема пищи.
 * @param items Список добавленных продуктов.
 * @param onAddClick Обработчик нажатия на кнопку добавления.
 */

@Composable
fun MealEntrySwitcher(
    title: String,
    @DrawableRes iconRes: Int,
    items: List<FoodItem>,
    onAddClick: () -> Unit
) {
    if (items.isEmpty()) {
        MealEntryButton(title, iconRes, 0, onAddClick)
    } else {
        MealEntryCard(title, iconRes, items, onAddClick)
    }
}

/**
 * Компонент кнопки добавления еды, отображаемой при пустом списке продуктов.
 *
 * @param title Название приема пищи.
 * @param iconRes Иконка для приема пищи.
 * @param calories Общее количество калорий (обычно 0 на этапе добавления).
 * @param onClick Действие при нажатии.
 */

@Composable
fun MealEntryButton(
    title: String,
    @DrawableRes iconRes: Int,
    calories: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Column(horizontalAlignment = Alignment.End) {
                Text("$calories", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Калорий", fontSize = 12.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }
}

/**
 * Карточка с добавленными продуктами и суммарными калориями.
 * Позволяет перейти к деталям конкретного продукта.
 *
 * @param title Название приема пищи.
 * @param iconRes Иконка приема пищи.
 * @param items Список добавленных продуктов.
 * @param onAddClick Обработчик добавления нового продукта.
 */

@Composable
fun MealEntryCard(
    title: String,
    @DrawableRes iconRes: Int,
    items: List<FoodItem>,
    onAddClick: () -> Unit
) {
    val totalCalories = items.sumOf { it.calories }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Button(
            onClick = onAddClick,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.weight(1f)
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text("$totalCalories", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)
                    Text("Калорий", fontSize = 12.sp, color = MaterialTheme.colorScheme.background)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.background)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(16.dp)
        ) {
            items.forEachIndexed { index, item ->
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable {}.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.background)
                            Text(item.amount, fontSize = 14.sp, color = MaterialTheme.colorScheme.background)
                        }
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.background)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        NutrientCell("Жиры", item.fat)
                        NutrientCell("Углев", item.carbs)
                        NutrientCell("Белк", item.protein)
                        NutrientCell("Калории", item.calories, highlight = true)
                    }

                    if (index != items.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.background.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Компонент ячейки с одним нутриентом в карточке еды.
 *
 * @param label Название нутриента.
 * @param value Значение нутриента.
 * @param highlight Выделить жирным, если true.
 */

@Composable
private fun NutrientCell(label: String, value: Int, highlight: Boolean = false) {
    val color = MaterialTheme.colorScheme.background
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 12.sp, color = color)
        Text(text = value.toString(), fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal, fontSize = 14.sp, color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun MealEntrySwitcherPreview() {
    var foodList by remember { mutableStateOf(emptyList<FoodItem>()) }

    MATheme {
        Column {
            MealEntrySwitcher(
                title = "Завтрак",
                iconRes = R.drawable.breakfast,
                items = foodList,
                onAddClick = {
                    foodList = foodList + FoodItem("Огурец", "100 г", 12, 12, 12, 48)
                }
            )
        }
    }
}

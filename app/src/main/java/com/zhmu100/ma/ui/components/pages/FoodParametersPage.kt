package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.diet.Meal
import com.zhmu100.ma.domain.viewModel.DietViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.food.FoodParametersTable
import com.zhmu100.ma.ui.data.Mineral
import com.zhmu100.ma.ui.data.Vitamin
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodParametersPage(
    navController: NavController? = null,
    viewModel: DietViewModel = koinViewModel()
) {
    val selectedFood = viewModel.selectedFood.value
    val amount = viewModel.portionAmount.value
    val unit = viewModel.portionUnit.value

    var expanded by remember { mutableStateOf(false) }

    BasePage(
        false,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { baseModifier ->
        val scrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header only with back button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BackButton(
                    text = "Назад",
                    onClick = { navController?.navigate(FoodAddScreen) }
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            // Product title and description below header
            Text(
                text = selectedFood?.name ?: "Неизвестный продукт",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = selectedFood?.description ?: "",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp)
            )
            // Amount input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.scale),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { viewModel.updatePortionAmount(it) },
                    label = { Text("+/-") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Unit input
            val units = listOf("г", "кг", "мл", "л", "шт")
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.list),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = unit,
                        onValueChange = { viewModel.updatePortionUnit(it) },
                        label = { Text("г, кг, мл, л, шт") },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    units.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updatePortionUnit(selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            RoundButton(
                "Сохранить",
                onClick = {
                    selectedFood?.let { food ->
                        val grams = amount.toDoubleOrNull() ?: 100.0
                        val adjustedFood = food.withPortion(grams)

                        val meal = Meal(
                            name = adjustedFood.name,
                            mealType = viewModel.selectedMealType.value,
                            foods = listOf(adjustedFood),
                            date = LocalDate.now().toString()
                        )

                        viewModel.addMeal(meal, onSuccess = {
                            navController?.navigate(FoodAddScreen)
                        }, onError = {
                            // показать ошибку
                        })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Отображение параметров
            Text(text = "В 100 $unit продукта:")
            selectedFood?.let {
                FoodParametersTable(
                    calories = it.calories,
                    protein = it.protein,
                    carbs = it.carbs,
                    saturatedFats = it.saturatedFats,
                    transFats = it.transFats,
                    fiber = it.fiber,
                    sugar = it.sugar,
                    vitamins = it.vitamins.map { v -> Vitamin(v.name, v.amount, v.unit) },
                    minerals = it.minerals.map { m -> Mineral(m.name, m.amount, m.unit) }
                )
            }
        }
    }
}

@Serializable
object FoodParametersScreen

@Preview(showBackground = true)
@Composable
private fun FoodParametersPagePreview() {
    MATheme {
        FoodParametersPage()
    }
}

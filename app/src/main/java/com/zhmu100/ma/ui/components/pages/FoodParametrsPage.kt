package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.*
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodParametersPage(
    navController: NavController? = null,
    name: String = "Персик Манго без Сахара",
    description: String = "Напиток",
    calories: Double = 2.0,
    protein: Double = 0.0,
    carbs: Double = 1.0,
    saturatedFats: Double = 0.0,
    transFats: Double = 0.0,
    fiber: Double = 0.0,
    sugar: Double = 0.0,
    vitamins: List<Vitamin> = listOf(Vitamin("A", 12.0, " мг")),
    minerals: List<Mineral> = listOf(Mineral("Калий", 30.0, " мг"))
) {
    var amount by remember { mutableStateOf("100") }
    var unit by remember { mutableStateOf("г") }

    BasePage(false, modifier = Modifier.background(MaterialTheme.colorScheme.background)) { baseModifier ->
        val scrollState = rememberScrollState()

        Column(
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
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
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
                    onValueChange = { amount = it },
                    label = { Text("+/-") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Unit input
            val units = listOf("г", "кг", "мл", "л", "шт")
            var expanded by remember { mutableStateOf(false) }

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
                        onValueChange = { unit = it },
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
                                unit = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RoundButton(
                "Сохранить",
                onClick = { navController?.navigate(FoodAddScreen) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            FoodParametersTable(
                calories = calories,
                protein = protein,
                carbs = carbs,
                saturatedFats = saturatedFats,
                transFats = transFats,
                fiber = fiber,
                sugar = sugar,
                vitamins = vitamins,
                minerals = minerals
            )
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

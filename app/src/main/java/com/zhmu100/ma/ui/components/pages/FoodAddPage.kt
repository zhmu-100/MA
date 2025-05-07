package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.diet.MealType
import com.zhmu100.ma.domain.viewModel.DietViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun FoodAddPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DietViewModel = koinViewModel()
) {
    val allFoods by viewModel.foods.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedMealType by rememberSaveable { mutableStateOf(MealType.MEAL_TYPE_BREAKFAST) }
    var showMealDropdown by remember { mutableStateOf(false) }

    val filteredItems by remember(searchQuery, allFoods) {
        derivedStateOf {
            if (searchQuery.isBlank()) allFoods
            else allFoods.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val currentDate by remember {
        derivedStateOf {
            LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())
            ).replaceFirstChar { it.titlecase() }
        }
    }

    BasePage(
        false,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) { baseModifier ->
        Box(modifier = baseModifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                // Верхняя панель
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        BackButton(
                            text = "Назад",
                            onClick = { navController?.navigate(FoodScreen) }
                        )
                    }
                    Text(
                        text = currentDate,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showMealDropdown = true }
                        ) {
                            Text(
                                text = selectedMealType.typeName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                        DropdownMenu(
                            expanded = showMealDropdown,
                            onDismissRequest = { showMealDropdown = false }
                        ) {
                            MealType.entries.forEach { type ->
                                if (type != MealType.MEAL_TYPE_UNSPECIFIED) {
                                    DropdownMenuItem(
                                        text = { Text(type.typeName) },
                                        onClick = {
                                            selectedMealType = type
                                            viewModel.selectMealType(type)
                                            showMealDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Вкладка "Еда"
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Еда", color = MaterialTheme.colorScheme.primary)
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Поисковая строка
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    placeholder = { Text("Поиск Еды") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null
                        )
                    }
                )

                // Список продуктов
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(count = filteredItems.count(), key = { filteredItems[it].id ?: "" }) { index ->
                        val food = filteredItems[index]
                        StringButton(
                            text = food.name,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.selectFoodForMeal(food)
                                navController?.navigate(FoodParametersScreen)
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }

            // Кнопка камеры по центру снизу
//            SquareIconButton(
//                iconResourceId = R.drawable.camera,
//                onClick = { navController?.navigate(FoodCameraScreen) },
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 24.dp)
//            )
        }
    }
}


@Serializable
object FoodAddScreen

@Preview(showBackground = true)
@Composable
private fun FoodAddPagePreview() {
    MATheme {
        FoodAddPage()
    }
}

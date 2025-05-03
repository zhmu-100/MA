package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun FoodAddPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    val meals = listOf("Завтрак", "Обед", "Ужин", "Перекус")
    var selectedMeal by remember { mutableStateOf(meals[0]) }
    var showMealDropdown by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    val allItems = listOf("Test11", "Test21", "Test31", "Test41", "Test51", "Test61", "Test1", "Test2", "Test3", "Test4", "Test5", "Test6")
    val filteredItems = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList() else
            allItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    val currentDate = remember {
        LocalDate.now().format(
            DateTimeFormatter.ofPattern("EEEE, MMM d", Locale("ru"))
        ).replaceFirstChar { it.uppercase() }
    }

    BasePage(
        false,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) { baseModifier ->
        Box(modifier = baseModifier.fillMaxSize()) {

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .verticalScroll(scrollState)

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
                                text = selectedMeal,
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
                            meals.forEach { meal ->
                                DropdownMenuItem(
                                    text = { Text(meal) },
                                    onClick = {
                                        selectedMeal = meal
                                        showMealDropdown = false
                                    }
                                )
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
                    Divider(
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

                filteredItems.forEachIndexed { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        StringButton(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp),
                            onClick = { navController?.navigate(FoodParametersScreen)  }
                        )
                    }

                    if (index != filteredItems.lastIndex) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            // Кнопка камеры по центру снизу
            SquareIconButton(
                iconResourceId = R.drawable.camera,
                onClick = { navController?.navigate(FoodCameraScreen) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
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

package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.DoubleButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import kotlin.math.round

@Composable
fun ProfileParametersPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        var heightSliderValue by remember { mutableStateOf(170f) }
        var weightSliderValue by remember { mutableStateOf(70f) }
        var ageSliderValue by remember { mutableStateOf(20f) }
        var gender by remember { mutableStateOf("Мужской") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Изменить параметры",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            InputLine(label = "Имя")
            Text("Рост ${round(heightSliderValue)} см")
            Slider(
                value = heightSliderValue,
                onValueChange = { heightSliderValue = it },
                valueRange = 100f..250f,
                steps = 149
            )
            Text("Вес ${round(weightSliderValue)} кг")
            Slider(
                value = weightSliderValue,
                onValueChange = { weightSliderValue = it },
                valueRange = 50f..150f,
                steps = 99
            )
            Text("Возраст ${round(ageSliderValue)} лет")
            Slider(
                value = ageSliderValue,
                onValueChange = { ageSliderValue = it },
                valueRange = 0f..100f,
                steps = 99
            )
            Text("Пол ${gender}")
            DoubleButton(
                leftText = "Мужской",
                rightText = "Женский",
                onLeftClick = { gender = "Мужской" },
                onRightClick = { gender = "Женский" }
            )
        }
    }
}


@Serializable
object ProfileParametersScreen

@Preview(showBackground = true)
@Composable
private fun ProfileParametersPagePreview() {
    MATheme {
        ProfileParametersPage()
    }
}
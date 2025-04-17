package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun TrainMapPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            Text(
                "Тренировка",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Black)
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("5000", "Шаги")
                    Stat("4:12", "км")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("0:40:00", "Длительность")
                    Stat("300", "ккал")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("120", "уд/мин")
                    Stat("9:14", "мин/км")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Пауза")
                }
                Button(
                    onClick = {navController?.navigate(TrainMoodScreen)},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Завершить")
                }
            }
        }
    }
}

@Composable
private fun Stat(statName: String, statValue: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(statName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(statValue)
    }
}

@Serializable
object TrainMapScreen

@Preview(showBackground = true)
@Composable
private fun TrainMapPagePreview() {
    MATheme {
        TrainMapPage()
    }
}
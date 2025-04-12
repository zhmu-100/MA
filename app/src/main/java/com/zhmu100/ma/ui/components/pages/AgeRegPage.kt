package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.inputs.AgeSelector
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun AgeRegPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        var ageSliderValue by remember { mutableStateOf(20f) }

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
                    "Возраст",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                "Введите ваш возраст",
                modifier = Modifier
                    .padding(vertical = 16.dp),
                fontSize = 14.sp
            )
            AgeSelector(
                onAgeSelected = { ageSliderValue = it.toFloat() },
                modifier = Modifier
                    .padding(top = 80.dp)
            )
            RoundButton(
                text = "Далее",
                onClick = { navController?.navigate(WeightRegScreen) },
                modifier = Modifier
                    .padding(top = 120.dp)
            )
        }
    }
}


@Serializable
object AgeRegScreen

@Preview(showBackground = true)
@Composable
private fun AgeRegPagePreview() {
    MATheme {
        AgeRegPage()
    }
}
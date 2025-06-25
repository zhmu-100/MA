package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BigIconButton
import com.zhmu100.ma.ui.components.buttons.DoubleButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun TrainCategoryPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(
        true,
        navIndex = 1,
        modifier = modifier,
        navController = navController
    ) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Text(
                "Тренировка",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            BigIconButton(
                text = "Статистика",
                drawableResId = R.drawable.bars,
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = { navController?.navigate(StatisticsScreen) }
            )
            Text("История тренировок", modifier = Modifier.padding(bottom = 8.dp))
            DoubleButton(
                leftText = "Пробежки",
                rightText = "Спортзал",
                onLeftClick = {
                    navController?.navigate(TrainDynamicHistoryScreen)
                },
                onRightClick = {
                    navController?.navigate(TrainStaticHistoryScreen)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text("Выбрать категорию", modifier = Modifier.padding(bottom = 8.dp))
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    TrainCategoryButton("Бег", R.drawable.run) {
                        navController?.navigate(
                            TrainMapScreen
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    TrainCategoryButton("Спортзал", R.drawable.gym) {
                        navController?.navigate(
                            TrainGymScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrainCategoryButton(text: String, icon: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SquareIconButton(
            icon,
            modifier = Modifier
                .size(150.dp)
                .shadow(2.dp, shape = RoundedCornerShape(25)),
            background = MaterialTheme.colorScheme.secondary,
            onClick = onClick
        )
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Serializable
object TrainCategoryScreen

@Preview(showBackground = true)
@Composable
private fun TrainCategoryPagePreview() {
    MATheme {
        TrainCategoryPage()
    }
}
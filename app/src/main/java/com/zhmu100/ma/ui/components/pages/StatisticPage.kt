package com.zhmu100.ma.ui.components.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun StatisticPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    TODO("Not yet implemented")
}

@Serializable
object StatisticsScreen

@Preview(showBackground = true)
@Composable
private fun StatisticPagePreview() {
    MATheme {
        StatisticPage()
    }
}
package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun IntroPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.intro_image),
                contentDescription = "Intro Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Начнем?",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Text(
                text = "Пожалуйста, заполните информацию о себе,\nчтобы мы могли предложить вам лучшие рекомендации.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 36.dp), textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            RoundButton(
                text = "Далее",
                onClick = { navController?.navigate(GenderRegScreen) },
                modifier = Modifier
                    .padding(bottom = 40.dp)
            )
        }
    }
}


@Serializable
object IntroScreen

@Preview(showBackground = true)
@Composable
private fun IntroPagePreview() {
    MATheme {
        IntroPage()

    }
}
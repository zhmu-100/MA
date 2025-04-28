package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun ProfileRegPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 16.dp),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Профиль",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                "Помогите нам лучше понять ваши потребности для достижения наилучших результатов",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ){
                ProfileImage(
                    url = "https://avatars.mds.yandex.net/i?id=973900345cef4fb385b6142051e2cd9b81e0ff0a-9856853-images-thumbs&n=13")
            }
            InputLine(
                label = "Имя",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                )
            InputLine(
                label = "Никнейм",
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
            InputLine(
                label = "Почта",
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
            InputLine(
                label = "Телефон",
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )

            RoundButton(
                text = "Далее",
                onClick = { navController?.navigate(GoalsRegScreen) },
                modifier = Modifier
                    .padding(top = 70.dp)
            )
        }
    }
}

@Composable
private fun ProfileImage(url: String) {
    Box {
        AsyncImage(
            model = url,
            contentDescription = "Profile picture",
            placeholder = painterResource(R.drawable.person),
            error = painterResource(R.drawable.person),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(LightGray)
        )
        ThemedIconButton(
            imageVector = Icons.Default.Create,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}


@Serializable
object ProfileRegScreen

@Preview(showBackground = true)
@Composable
private fun ProfileRegPagePreview() {
    MATheme {
        ProfileRegPage()
    }
}
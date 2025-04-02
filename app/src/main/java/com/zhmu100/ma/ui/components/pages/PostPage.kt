package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun PostPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val toolIconColors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background
            )
            val url =
                "https://avatars.mds.yandex.net/i?id=973900345cef4fb385b6142051e2cd9b81e0ff0a-9856853-images-thumbs&n=13"
            val text =
                "Я снова оттягивала этот момент, но больше нельзя. Диван такой мягкий, сериальчик такой интересный... \n" +
                        "Но нет! Сегодня день ног, и плевать, что они уже ноют от одной мысли о приседаниях. Нужно оторвать себя от этого уютного плена и заставить двигаться. Ладно, уговорила, сама себя. Пойду, отмучаюсь."

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
                    "Новый пост",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                SaveButton(text = "Опубликовать", modifier = Modifier.align(Alignment.CenterEnd))
            }
            url?.let {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(15))
                        .padding(bottom = 8.dp)
                ) {
                    PostImage(url, Modifier.padding(8.dp))
                    SquareIconButton(
                        Icons.Default.Delete,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Row(modifier = Modifier.padding(8.dp)) {
                IconButton({}, colors = toolIconColors) {
                    Icon(painter = painterResource(R.drawable.image), "Image Icon")
                }
                IconButton({}, colors = toolIconColors) {
                    Icon(painter = painterResource(R.drawable.play_circle), "Video Icon")
                }
            }
            HorizontalDivider(thickness = 1.dp, color = LightGray)
            Text(text)
        }
    }
}

@Composable
private fun PostImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = "Post Image",
        contentScale = ContentScale.Crop,
        modifier = modifier.size(200.dp)
    )
}

@Serializable
object PostScreen

@Preview(showBackground = true)
@Composable
private fun PostPagePreview() {
    MATheme {
        PostPage()
    }
}
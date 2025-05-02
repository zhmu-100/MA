package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun FoodCameraPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {

            val url =
                "https://avatars.mds.yandex.net/i?id=973900345cef4fb385b6142051e2cd9b81e0ff0a-9856853-images-thumbs&n=13"

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(FoodAddScreen) })

                SaveButton(
                    text = "Сохранить",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { navController?.navigate(FoodAddScreen)} )
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

            HorizontalDivider(thickness = 1.dp, color = LightGray)
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
object FoodCameraScreen

@Preview(showBackground = true)
@Composable
private fun FoodCameraPagePreview() {
    MATheme {
        FoodCameraPage()
    }
}
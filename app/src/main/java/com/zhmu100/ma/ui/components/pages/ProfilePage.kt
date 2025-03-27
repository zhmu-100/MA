package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.BigIconButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
                BackButton(text = "Назад")
            BackButton(text = "•••", isIconActive = false)
        }
        ProfileImage(
            url = "https://avatars.mds.yandex.net/i?id=973900345cef4fb385b6142051e2cd9b81e0ff0a-9856853-images-thumbs&n=13"
        )
        Text("Login")
        Text("online", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        BigIconButton(text = "Мои параметры", drawableResId = R.drawable.ruler)
        BigIconButton(text = "Напоминания", drawableResId = R.drawable.alarm)
        BigIconButton(text = "Мои устройства", drawableResId = R.drawable.devices)
        BigIconButton(text = "Статистика", drawableResId = R.drawable.bars)
        Text(
            "Мои записи",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.inversePrimary
        )
        RoundButton("Создать запись")
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

@Preview(showBackground = true)
@Composable
private fun ProfilePagePreview() {
    MATheme {
        ProfilePage()
    }
}
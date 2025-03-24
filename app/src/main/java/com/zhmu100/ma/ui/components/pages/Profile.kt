package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.BigIconButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun Profile(modifier: Modifier = Modifier) {
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
        BigIconButton(text = "Мой Вес", drawableResId = R.drawable.ruler)
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

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    MATheme {
        Profile()
    }
}
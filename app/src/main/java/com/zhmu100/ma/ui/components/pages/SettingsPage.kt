package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.components.buttons.ToggleButton
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun SettingsPage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(text = "Назад", modifier = Modifier.align(Alignment.CenterStart))
            Text(
                "Настройки",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить тему")
            ToggleButton()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить язык")
            StringButton("Русский")
        }
        StringButton("Изменить личные данные")
        RoundButton("Сохранить")
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsPagePreview() {
    MATheme {
        SettingsPage()
    }
}
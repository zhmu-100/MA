package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun DevicePage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            BackButton(text = "Назад", modifier = Modifier.align(Alignment.CenterStart))
            SaveButton(text = "Сохранить", modifier = Modifier.align(Alignment.CenterEnd))
        }
        Text(
            "Добавить устройство",
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DevicePagePreview() {
    MATheme {
        DevicePage()
    }
}
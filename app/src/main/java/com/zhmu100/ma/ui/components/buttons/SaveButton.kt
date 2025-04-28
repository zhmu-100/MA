package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

/**
 * Компонент кнопки сохранения с опциональной иконкой стрелки.
 *
 * @param modifier Модификатор для настройки внешнего вида и расположения кнопки
 * @param text Текст кнопки (по умолчанию "Сохранить")
 * @param onClick Обработчик нажатия на кнопку
 * @param isIconActive Флаг отображения иконки стрелки (по умолчанию true)
 */
@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    text: String = "Сохранить",
    onClick: () -> Unit = {},
    isIconActive: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary, White),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
        modifier = modifier.height(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            if (isIconActive) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Arrow Right"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    MATheme {
        SaveButton(text = text, onClick = { text = "•••" }, isIconActive = true)
    }
}
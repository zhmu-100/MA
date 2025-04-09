package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент тематической кнопки с иконкой (из векторного изображения).
 *
 * @param imageVector Векторное изображение для иконки кнопки
 * @param modifier Модификатор для настройки внешнего вида и расположения кнопки
 * @param onClick Обработчик нажатия на кнопку
 */
@Composable
fun ThemedIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = BaseThemedIconButton(
    modifier = modifier,
    onClick = onClick
) {
    Icon(imageVector = imageVector, null)
}

/**
 * Компонент тематической кнопки с иконкой (из ресурсов).
 *
 * @param drawableResId ID ресурса изображения для иконки кнопки
 * @param modifier Модификатор для настройки внешнего вида и расположения кнопки
 * @param onClick Обработчик нажатия на кнопку
 */
@Composable
fun ThemedIconButton(
    drawableResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = BaseThemedIconButton(
    modifier = modifier,
    onClick = onClick
) {
    Icon(painter = painterResource(drawableResId), null)
}

@Composable
private fun BaseThemedIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick, colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
private fun IconButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    Column {
        Text(text)
        MATheme {
            ThemedIconButton(Icons.Default.Settings, onClick = { text = "Test1" })
        }
    }
}
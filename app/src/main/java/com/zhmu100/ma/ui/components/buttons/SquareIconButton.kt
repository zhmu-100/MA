package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент квадратной кнопки с иконкой и закругленными углами.
 *
 * @param imageVector Векторное изображение для иконки кнопки
 * @param modifier Модификатор для настройки внешнего вида и расположения кнопки
 * @param background Цвет фона кнопки (по умолчанию tertiary цвет из темы)
 * @param onClick Обработчик нажатия на кнопку
 */
@Composable
fun SquareIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit = {}
) = BaseSquareIconButton(
    modifier = modifier,
    background = background,
    onClick = onClick
) {
    Icon(
        imageVector = imageVector, null,
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

/**
 * Компонент квадратной кнопки с иконкой (из ресурсов) и закругленными углами.
 *
 * @param iconResourceId ID ресурса изображения для иконки кнопки
 * @param modifier Модификатор для настройки внешнего вида и расположения кнопки
 * @param background Цвет фона кнопки (по умолчанию tertiary цвет из темы)
 * @param onClick Обработчик нажатия на кнопку
 */
@Composable
fun SquareIconButton(
    iconResourceId: Int,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit = {}
) = BaseSquareIconButton(
    modifier = modifier,
    background = background,
    onClick = onClick
) {
    Icon(
        painter = painterResource(iconResourceId), null,
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

@Composable
private fun BaseSquareIconButton(
    modifier: Modifier,
    background: Color,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = background,
            contentColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .background(color = background, shape = RoundedCornerShape(25))
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareIconButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    Column {
        Text(text)
        MATheme {
            SquareIconButton(Icons.Default.Delete, onClick = { text = "Test1" })
        }
    }
}
package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент двойной кнопки с разделителем между двумя действиями.
 *
 * @param leftText Текст для левой кнопки
 * @param rightText Текст для правой кнопки
 * @param onLeftClick Обработчик нажатия левой кнопки
 * @param onRightClick Обработчик нажатия правой кнопки
 */
@Composable
fun DoubleButton(
    leftText: String,
    rightText: String,
    modifier: Modifier = Modifier,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    val buttonModifier = Modifier
        .fillMaxHeight()
        .background(MaterialTheme.colorScheme.inversePrimary)
    Row(
        modifier = modifier
            .height(50.dp)
            .border(0.dp, Color.Black, RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        // Left button
        Box(
            modifier = buttonModifier
                .weight(1f)
                .clickable { onLeftClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = leftText,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold
            )
        }
        // Divider
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        )
        // Right button
        Box(
            modifier = buttonModifier
                .weight(1f)
                .clickable { onRightClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rightText,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreen() {
    var text1 by remember { mutableStateOf("Test") }
    var text2 by remember { mutableStateOf("Test") }
    MATheme {
        DoubleButton(
            leftText = text1,
            rightText = text2,
            onLeftClick = { text1 = "Test1" },
            onRightClick = { text2 = "Test2" }
        )
    }
}
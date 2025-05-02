package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Многострочное текстовое поле с тенью и закругленными углами.
 * Компонент предоставляет возможность ввода многострочного текста с кастомным оформлением
 *
 * @param text Текущее значение текстового поля
 * @param modifier [Modifier] для настройки внешнего вида и расположения компонента
 * @param placeholder Текст-подсказка, отображаемый когда поле пустое
 * @param onTextChanged Обработчик изменения текста (новое значение передается как параметр)
 *
 * Пример использования:
 * ```
 * var text by remember { mutableStateOf("") }
 * MultiLineTextField(
 *     text = text,
 *     placeholder = "Введите ваш комментарий",
 *     onTextChanged = { text = it }
 * )
 * ```
 */
@Composable
fun MultiLineTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onTextChanged: (String) -> Unit = {},
    readOnly: Boolean = false
) {
    TextField(
        readOnly = readOnly,
        singleLine = false,
        value = text,
        onValueChange = onTextChanged,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier.shadow(2.dp, shape = RoundedCornerShape(15))
    )
}

@Preview(showBackground = true)
@Composable
private fun MultiLineTextFieldPreview() {
    MATheme {
        var text by remember { mutableStateOf("Test") }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text)
            MultiLineTextField(
                text = text,
                placeholder = "ДВДЖЫД",
                onTextChanged = { text = it }
            )
        }
    }
}
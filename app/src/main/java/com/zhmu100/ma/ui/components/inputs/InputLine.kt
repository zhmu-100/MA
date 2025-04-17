package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент текстового поля ввода с базовыми функциями.
 *
 * @param modifier Модификатор для настройки внешнего вида и расположения компонента
 * @param label Текстовая метка над полем ввода (опционально)
 * @param password Флаг, указывающий нужно ли скрывать вводимый текст (для паролей)
 * @param onTextChanged Обработчик изменения текста (возвращает текущее значение)
 * @param placeholder Текст показываемый до пользовательского ввода
 */
@Composable
fun InputLine(
    modifier: Modifier = Modifier,
    label: String = "",
    password: Boolean = false,
    onTextChanged: (String) -> Unit = {},
    placeholder: String = ""
) {
    var textState by remember { mutableStateOf("") }
    val shape = CircleShape

    Column(modifier = modifier) {
        Text(text = label)
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                onTextChanged(it)
            },
            placeholder = { Text(placeholder) },
            singleLine = true,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape)
                .background(MaterialTheme.colorScheme.background, shape)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputLinePreview() {
    MATheme {
        var text by remember { mutableStateOf("Test") }
        InputLine(
            label = text,
            password = false,
            onTextChanged = { text = "Test1" }
        )
    }
}
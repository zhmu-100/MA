package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Компонент текстового поля ввода без границ для именования объектов пользователем.
 *
 * @param modifier Модификатор для настройки внешнего вида и расположения компонента
 * @param label Текстовая метка над полем ввода (опционально)
 * @param onTextChanged Обработчик изменения текста (возвращает текущее значение)
 * @param placeholder Текст показываемый до пользовательского ввода
 */
@Composable
fun BorderlessInputLine(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    placeholder: String = ""
) {
    var textState by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                onTextChanged(it)
            },
            placeholder = {
                Text(
                    placeholder,
                    textAlign = TextAlign.Center,
                    color = LightGray,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BorderlessInputLinePreview() {
    MATheme {
        var text by remember { mutableStateOf("Test") }
        Text(text)
        BorderlessInputLine(
            placeholder = "Введите свой текст здесь",
            onTextChanged = { text = it }
        )
    }
}
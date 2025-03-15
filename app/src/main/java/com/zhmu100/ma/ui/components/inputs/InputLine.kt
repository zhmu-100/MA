package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun InputLine(
    modifier: Modifier = Modifier,
    label: String = "",
    password: Boolean = false,
    onTextChanged: (String) -> Unit = {}
) {
    var textState by remember { mutableStateOf("") }
    val shape = CircleShape

    Column(modifier = modifier) {
        Text(text = label)
        BasicTextField(
            value = textState,
            onValueChange = {
                textState = it
                onTextChanged(it)
            },
            singleLine = true,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape)
                .background(MaterialTheme.colorScheme.background, shape)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLinePreview() {
    MATheme {
        InputLine(
            label = "Почта",
            password = false
        )
    }
}
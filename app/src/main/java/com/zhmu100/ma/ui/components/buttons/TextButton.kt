package com.zhmu100.ma.ui.components.buttons

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun StringButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StringButtonPreview() {
    MATheme {
        StringButton(
            "Зарегистрируйся"
        )
    }
}
package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme


@Composable
fun RoundButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {} ) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    MATheme {
        RoundButton(
            "Вход"
        )
    }
}
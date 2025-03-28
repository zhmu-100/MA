package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.theme.MATheme


@Composable
fun SquareButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    MATheme {
        SquareButton(
            text, onClick = { text = "Test1" }
        )
    }
}
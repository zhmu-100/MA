package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun SquareIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick, colors = IconButtonDefaults.iconButtonColors(
            containerColor = background,
            contentColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .size(48.dp)
            .background(color = background, shape = RoundedCornerShape(25))
    ) {
        Icon(
            imageVector = imageVector,
            null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SquareIconButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    Column {
        Text(text)
        MATheme {
            SquareIconButton(Icons.Default.Delete, onClick = { text = "Test1" })
        }
    }
}
package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.theme.MATheme


@Composable
fun ThemedIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick, colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            null
        )
    }
}

@Composable
fun ThemedIconButton(
    drawableResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick, colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(drawableResId),
            null
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun IconButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    Column {
        Text(text)
        MATheme {
            ThemedIconButton(Icons.Default.Settings, onClick = { text = "Test1" })
        }
    }
}
package com.zhmu100.ma.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.components.Greeting
import com.zhmu100.ma.ui.theme.MATheme

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MATheme {
        Greeting(
            "Android",
            Modifier
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}
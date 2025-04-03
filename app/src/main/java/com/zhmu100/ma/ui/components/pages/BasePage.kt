package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.NavBar
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun BasePage(
    hasNavBar: Boolean,
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    content: @Composable (Modifier) -> Unit
) {
    val bottomBar: @Composable (() -> Unit) = if (hasNavBar) {
        { NavBar(4, {}, modifier = Modifier.padding(vertical = 8.dp), navController) }
    } else {
        {}
    }

    Scaffold(
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        content(
            modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasePagePreview() {
    MATheme {
        BasePage(true) {}
    }
}
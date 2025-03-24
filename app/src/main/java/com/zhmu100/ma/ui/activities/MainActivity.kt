package com.zhmu100.ma.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.components.Greeting
import com.zhmu100.ma.ui.components.NavBar
import com.zhmu100.ma.ui.components.inputs.HeightSelector
import com.zhmu100.ma.ui.components.pages.Profile
import com.zhmu100.ma.ui.theme.MATheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MATheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = { NavBar(4, {}) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Profile(modifier = Modifier.consumeWindowInsets(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MATheme {
        MainScreen()
    }
}
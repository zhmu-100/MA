package com.zhmu100.ma.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhmu100.ma.ui.components.NavBar
import com.zhmu100.ma.ui.components.pages.DevicePage
import com.zhmu100.ma.ui.components.pages.DeviceScreen
import com.zhmu100.ma.ui.components.pages.DevicesPage
import com.zhmu100.ma.ui.components.pages.DevicesScreen
import com.zhmu100.ma.ui.components.pages.PostPage
import com.zhmu100.ma.ui.components.pages.PostScreen
import com.zhmu100.ma.ui.components.pages.ProfilePage
import com.zhmu100.ma.ui.components.pages.ProfileScreen
import com.zhmu100.ma.ui.components.pages.ReminderPage
import com.zhmu100.ma.ui.components.pages.ReminderScreen
import com.zhmu100.ma.ui.components.pages.RemindersPage
import com.zhmu100.ma.ui.components.pages.RemindersScreen
import com.zhmu100.ma.ui.components.pages.SettingsPage
import com.zhmu100.ma.ui.components.pages.SettingsScreen
import com.zhmu100.ma.ui.theme.MATheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MATheme {
                val navContoller = rememberNavController()
                NavHost(navController = navContoller, startDestination = ProfileScreen) {
                    composable<ProfileScreen> { ProfilePage(navController = navContoller) }
                    composable<DevicesScreen> { DevicesPage(navController = navContoller) }
                    composable<DeviceScreen> { DevicePage(navController = navContoller) }
                    composable<PostScreen> { PostPage(navController = navContoller) }
                    composable<RemindersScreen> { RemindersPage(navController = navContoller) }
                    composable<ReminderScreen> { ReminderPage(navController = navContoller) }
                    composable<SettingsScreen> { SettingsPage(navController = navContoller) }
//                    composable<StatisticsScreen> {  }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = { NavBar(4, {}, modifier = Modifier.padding(vertical = 8.dp)) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ProfilePage(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MATheme {
        MainScreen()
    }
}
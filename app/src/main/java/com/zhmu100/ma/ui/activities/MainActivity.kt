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
import com.zhmu100.ma.ui.components.pages.ActivityLevelRegPage
import com.zhmu100.ma.ui.components.pages.ActivityLevelRegScreen
import com.zhmu100.ma.ui.components.pages.AgeRegPage
import com.zhmu100.ma.ui.components.pages.AgeRegScreen
import com.zhmu100.ma.ui.components.pages.DevicePage
import com.zhmu100.ma.ui.components.pages.DeviceScreen
import com.zhmu100.ma.ui.components.pages.DevicesPage
import com.zhmu100.ma.ui.components.pages.DevicesScreen
import com.zhmu100.ma.ui.components.pages.GenderRegPage
import com.zhmu100.ma.ui.components.pages.GenderRegScreen
import com.zhmu100.ma.ui.components.pages.GoalsRegPage
import com.zhmu100.ma.ui.components.pages.GoalsRegScreen
import com.zhmu100.ma.ui.components.pages.HeightRegPage
import com.zhmu100.ma.ui.components.pages.HeightRegScreen
import com.zhmu100.ma.ui.components.pages.IntroPage
import com.zhmu100.ma.ui.components.pages.IntroScreen
import com.zhmu100.ma.ui.components.pages.LoginPage
import com.zhmu100.ma.ui.components.pages.LoginScreen
import com.zhmu100.ma.ui.components.pages.NewPasswordPage
import com.zhmu100.ma.ui.components.pages.NewPasswordScreen
import com.zhmu100.ma.ui.components.pages.PostPage
import com.zhmu100.ma.ui.components.pages.PostScreen
import com.zhmu100.ma.ui.components.pages.ProfilePage
import com.zhmu100.ma.ui.components.pages.ProfileRegPage
import com.zhmu100.ma.ui.components.pages.ProfileRegScreen
import com.zhmu100.ma.ui.components.pages.ProfileParametersPage
import com.zhmu100.ma.ui.components.pages.ProfileParametersScreen
import com.zhmu100.ma.ui.components.pages.ProfileScreen
import com.zhmu100.ma.ui.components.pages.RegisterPage
import com.zhmu100.ma.ui.components.pages.RegisterScreen
import com.zhmu100.ma.ui.components.pages.ResetPasswordPage
import com.zhmu100.ma.ui.components.pages.ResetPasswordScreen
import com.zhmu100.ma.ui.components.pages.ReminderPage
import com.zhmu100.ma.ui.components.pages.ReminderScreen
import com.zhmu100.ma.ui.components.pages.RemindersPage
import com.zhmu100.ma.ui.components.pages.RemindersScreen
import com.zhmu100.ma.ui.components.pages.SettingsPage
import com.zhmu100.ma.ui.components.pages.SettingsScreen
import com.zhmu100.ma.ui.components.pages.WeightRegPage
import com.zhmu100.ma.ui.components.pages.WeightRegScreen
import com.zhmu100.ma.ui.components.pages.StatisticPage
import com.zhmu100.ma.ui.components.pages.StatisticsScreen

import com.zhmu100.ma.ui.theme.MATheme

/**
 * Главная Activity приложения, содержащая навигационный граф.
 *
 * Особенности реализации:
 * - Включает edge-to-edge отображение (enableEdgeToEdge)
 * - Использует кастомную тему MATheme
 * - Создает и передает NavController во все экраны
 * - Определяет начальный экран
 * Все экраны получают navController для навигации между ними
 */
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
                    composable<PostScreen> { IntroPage(navController = navContoller) }
                    composable<RemindersScreen> { RemindersPage(navController = navContoller) }
                    composable<ReminderScreen> { ReminderPage(navController = navContoller) }
                    composable<SettingsScreen> { SettingsPage(navController = navContoller) }
                    composable<IntroScreen> { IntroPage(navController = navContoller) }
                    composable<GenderRegScreen> { GenderRegPage(navController = navContoller) }
                    composable<AgeRegScreen> { AgeRegPage(navController = navContoller) }
                    composable<WeightRegScreen> { WeightRegPage(navController = navContoller) }
                    composable<HeightRegScreen> { HeightRegPage(navController = navContoller) }
                    composable<GoalsRegScreen> { GoalsRegPage(navController = navContoller) }
                    composable<ActivityLevelRegScreen> { ActivityLevelRegPage(navController = navContoller) }
                    composable<ProfileRegScreen> { ProfileRegPage(navController = navContoller) }
                    composable<StatisticsScreen> { StatisticPage(navController = navContoller) }
                    composable<ProfileParametersScreen> { ProfileParametersPage(navController = navContoller) }
                    composable<LoginScreen> { LoginPage(navController = navContoller) }
                    composable<RegisterScreen> { RegisterPage(navController = navContoller) }
                    composable<ResetPasswordScreen> { ResetPasswordPage(navController = navContoller) }
                    composable<NewPasswordScreen> { NewPasswordPage(navController = navContoller) }
                }
            }
        }
    }
}

@Composable
private fun MainScreen() {
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
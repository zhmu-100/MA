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
import com.zhmu100.ma.ui.components.pages.FeedPage
import com.zhmu100.ma.ui.components.pages.FeedScreen
import com.zhmu100.ma.ui.components.pages.FoodAddPage
import com.zhmu100.ma.ui.components.pages.FoodAddScreen
import com.zhmu100.ma.ui.components.pages.FoodCameraPage
import com.zhmu100.ma.ui.components.pages.FoodCameraScreen
import com.zhmu100.ma.ui.components.pages.FoodPage
import com.zhmu100.ma.ui.components.pages.FoodParametersPage
import com.zhmu100.ma.ui.components.pages.FoodParametersScreen
import com.zhmu100.ma.ui.components.pages.FoodScreen
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
import com.zhmu100.ma.ui.components.pages.TrainCategoryPage
import com.zhmu100.ma.ui.components.pages.TrainCategoryScreen
import com.zhmu100.ma.ui.components.pages.TrainGymPage
import com.zhmu100.ma.ui.components.pages.TrainGymScreen
import com.zhmu100.ma.ui.components.pages.TrainMapPage
import com.zhmu100.ma.ui.components.pages.TrainMapScreen
import com.zhmu100.ma.ui.components.pages.TrainMoodPage
import com.zhmu100.ma.ui.components.pages.TrainMoodScreen

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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ProfileScreen) {
                    composable<ProfileScreen> { ProfilePage(navController = navController) }
                    composable<DevicesScreen> { DevicesPage(navController = navController) }
                    composable<DeviceScreen> { DevicePage(navController = navController) }
                    composable<PostScreen> { IntroPage(navController = navController) }
                    composable<RemindersScreen> { RemindersPage(navController = navController) }
                    composable<ReminderScreen> { ReminderPage(navController = navController) }
                    composable<SettingsScreen> { SettingsPage(navController = navController) }
                    composable<IntroScreen> { IntroPage(navController = navController) }
                    composable<GenderRegScreen> { GenderRegPage(navController = navController) }
                    composable<AgeRegScreen> { AgeRegPage(navController = navController) }
                    composable<WeightRegScreen> { WeightRegPage(navController = navController) }
                    composable<HeightRegScreen> { HeightRegPage(navController = navController) }
                    composable<GoalsRegScreen> { GoalsRegPage(navController = navController) }
                    composable<ActivityLevelRegScreen> { ActivityLevelRegPage(navController = navController) }
                    composable<ProfileRegScreen> { ProfileRegPage(navController = navController) }
                    composable<StatisticsScreen> { StatisticPage(navController = navController) }
                    composable<PostScreen> { PostPage(navController = navController) }
                    composable<FeedScreen> { FeedPage(navController = navController) }
                    composable<FoodScreen> { FoodPage(navController = navController) }
                    composable<FoodAddScreen> { FoodAddPage(navController = navController) }
                    composable<FoodCameraScreen> { FoodCameraPage(navController = navController) }
                    composable<FoodParametersScreen> { FoodParametersPage(navController = navController) }
                    composable<ProfileParametersScreen> { ProfileParametersPage(navController = navController) }
                    composable<LoginScreen> { LoginPage(navController = navController) }
                    composable<RegisterScreen> { RegisterPage(navController = navController) }
                    composable<ResetPasswordScreen> { ResetPasswordPage(navController = navController) }
                    composable<NewPasswordScreen> { NewPasswordPage(navController = navController) }
                    composable<TrainCategoryScreen> { TrainCategoryPage(navController = navController) }
                    composable<TrainMapScreen> { TrainMapPage(navController = navController) }
                    composable<TrainGymScreen> { TrainGymPage(navController = navController) }
                    composable<TrainMoodScreen> { TrainMoodPage(navController = navController) }
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
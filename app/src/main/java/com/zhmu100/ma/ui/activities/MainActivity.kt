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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhmu100.ma.di.networkModule
import com.zhmu100.ma.di.storageModule
import com.zhmu100.ma.di.viewModelModule
import com.zhmu100.ma.domain.viewModel.AuthViewModel
import com.zhmu100.ma.domain.viewModel.DietViewModel
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
import com.zhmu100.ma.domain.viewModel.SettingsViewModel
import com.zhmu100.ma.domain.viewModel.TrainingViewModel
import com.zhmu100.ma.ui.components.NavBar
import com.zhmu100.ma.ui.components.pages.ActivityLevelRegPage
import com.zhmu100.ma.ui.components.pages.ActivityLevelRegScreen
import com.zhmu100.ma.ui.components.pages.AgeRegPage
import com.zhmu100.ma.ui.components.pages.AgeRegScreen
import com.zhmu100.ma.ui.components.pages.DevicePage
import com.zhmu100.ma.ui.components.pages.DeviceScreen
import com.zhmu100.ma.ui.components.pages.DevicesPage
import com.zhmu100.ma.ui.components.pages.DevicesScreen
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
import com.zhmu100.ma.ui.components.pages.NotePage
import com.zhmu100.ma.ui.components.pages.NoteScreen
import com.zhmu100.ma.ui.components.pages.NotesPage
import com.zhmu100.ma.ui.components.pages.NotesScreen
import com.zhmu100.ma.ui.components.pages.PostPage
import com.zhmu100.ma.ui.components.pages.PostScreen
import com.zhmu100.ma.ui.components.pages.ProfilePage
import com.zhmu100.ma.ui.components.pages.ProfileParametersPage
import com.zhmu100.ma.ui.components.pages.ProfileParametersScreen
import com.zhmu100.ma.ui.components.pages.ProfileRegPage
import com.zhmu100.ma.ui.components.pages.ProfileRegScreen
import com.zhmu100.ma.ui.components.pages.ProfileScreen
import com.zhmu100.ma.ui.components.pages.RegisterPage
import com.zhmu100.ma.ui.components.pages.RegisterScreen
import com.zhmu100.ma.ui.components.pages.ReminderPage
import com.zhmu100.ma.ui.components.pages.ReminderScreen
import com.zhmu100.ma.ui.components.pages.RemindersPage
import com.zhmu100.ma.ui.components.pages.RemindersScreen
import com.zhmu100.ma.ui.components.pages.ResetPasswordPage
import com.zhmu100.ma.ui.components.pages.ResetPasswordScreen
import com.zhmu100.ma.ui.components.pages.SettingsPage
import com.zhmu100.ma.ui.components.pages.SettingsScreen
import com.zhmu100.ma.ui.components.pages.StatisticPage
import com.zhmu100.ma.ui.components.pages.StatisticsScreen
import com.zhmu100.ma.ui.components.pages.TrainCategoryPage
import com.zhmu100.ma.ui.components.pages.TrainCategoryScreen
import com.zhmu100.ma.ui.components.pages.TrainDynamicHistoryPage
import com.zhmu100.ma.ui.components.pages.TrainDynamicHistoryScreen
import com.zhmu100.ma.ui.components.pages.TrainGymPage
import com.zhmu100.ma.ui.components.pages.TrainGymScreen
import com.zhmu100.ma.ui.components.pages.TrainMapPage
import com.zhmu100.ma.ui.components.pages.TrainMapScreen
import com.zhmu100.ma.ui.components.pages.TrainMoodPage
import com.zhmu100.ma.ui.components.pages.TrainMoodScreen
import com.zhmu100.ma.ui.components.pages.TrainStaticHistoryPage
import com.zhmu100.ma.ui.components.pages.TrainStaticHistoryScreen
import com.zhmu100.ma.ui.components.pages.WeightRegPage
import com.zhmu100.ma.ui.components.pages.WeightRegScreen
import com.zhmu100.ma.ui.theme.MATheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.core.context.GlobalContext.startKoin

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

        startKoin {
            androidContext(this@MainActivity)
            modules(
                networkModule,
                storageModule,
                viewModelModule
            )
        }

        enableEdgeToEdge()
        setContent {
            KoinContext {
                val startDestination = remember { mutableStateOf<Any?>(null) }
                val settingsViewModel: SettingsViewModel = koinViewModel()
                MATheme(settingsViewModel = settingsViewModel) {
                    val navController = rememberNavController()
                    val profileViewModel: ProfileViewModel = koinViewModel()
                    val trainingViewModel: TrainingViewModel = koinViewModel()
                    val dietViewModel: DietViewModel = koinViewModel()
                    val authViewModel: AuthViewModel = koinViewModel()
                    LaunchedEffect(Unit) {
                        val isValid = authViewModel.validateToken()
                        if (!isValid) {
                            authViewModel.refreshToken()
                        }
                        startDestination.value = if (isValid) ProfileScreen else LoginScreen
                    }

                    startDestination.value?.let { route ->
                        NavHost(navController = navController, startDestination = route) {
                            composable<ProfileScreen> {
                                ProfilePage(
                                    navController = navController,
                                    viewModel = profileViewModel
                                )
                            }
                            composable<ProfileParametersScreen> {
                                ProfileParametersPage(
                                    navController = navController,
                                    viewModel = profileViewModel
                                )
                            }
                            composable<DevicesScreen> { DevicesPage(navController = navController) }
                            composable<DeviceScreen> { DevicePage(navController = navController) }
                            composable<PostScreen> { PostPage(navController = navController) }
                            composable<FeedScreen> { FeedPage(navController = navController) }
                            composable<FoodScreen> {
                                FoodPage(
                                    navController = navController,
                                    viewModel = dietViewModel
                                )
                            }
                            composable<FoodAddScreen> {
                                FoodAddPage(
                                    navController = navController,
                                    viewModel = dietViewModel
                                )
                            }
                            composable<FoodCameraScreen> { FoodCameraPage(navController = navController) }
                            composable<FoodParametersScreen> {
                                FoodParametersPage(
                                    navController = navController,
                                    viewModel = dietViewModel
                                )
                            }
                            composable<RemindersScreen> { RemindersPage(navController = navController) }
                            composable("$ReminderScreen/{reminderId}") { backStackEntry ->
                                val reminderId = backStackEntry.arguments?.getString("reminderId")
                                ReminderPage(navController = navController, reminderId = reminderId)
                            }
                            composable<ReminderScreen> { ReminderPage(navController = navController) }
                            composable<SettingsScreen> {
                                SettingsPage(
                                    navController = navController,
                                    viewModel = settingsViewModel
                                )
                            }
                            composable<IntroScreen> { IntroPage(navController = navController) }
                            composable<GenderRegScreen> { GenderRegPage(navController = navController) }
                            composable<AgeRegScreen> { AgeRegPage(navController = navController) }
                            composable<WeightRegScreen> { WeightRegPage(navController = navController) }
                            composable<HeightRegScreen> { HeightRegPage(navController = navController) }
                            composable<GoalsRegScreen> { GoalsRegPage(navController = navController) }
                            composable<ActivityLevelRegScreen> { ActivityLevelRegPage(navController = navController) }
                            composable<ProfileRegScreen> { ProfileRegPage(navController = navController) }
                            composable<StatisticsScreen> { StatisticPage(navController = navController) }
                            composable<LoginScreen> { LoginPage(navController = navController) }
                            composable<RegisterScreen> { RegisterPage(navController = navController) }
                            composable<ResetPasswordScreen> { ResetPasswordPage(navController = navController) }
                            composable<NewPasswordScreen> { NewPasswordPage(navController = navController) }
                            composable<TrainCategoryScreen> { TrainCategoryPage(navController = navController) }
                            composable<TrainMapScreen> {
                                TrainMapPage(
                                    navController = navController,
                                    trainViewModel = trainingViewModel
                                )
                            }
                            composable<TrainGymScreen> {
                                TrainGymPage(
                                    navController = navController,
                                    trainViewModel = trainingViewModel
                                )
                            }
                            composable<TrainMoodScreen> {
                                TrainMoodPage(
                                    navController = navController,
                                    trainViewModel = trainingViewModel
                                )
                            }
                            composable<TrainDynamicHistoryScreen> {
                                TrainDynamicHistoryPage(
                                    navController = navController
                                )
                            }
                            composable<TrainStaticHistoryScreen> {
                                TrainStaticHistoryPage(
                                    navController = navController
                                )
                            }
                            composable<NotesScreen> { NotesPage(navController = navController) }
                            composable("$NoteScreen/{noteId}") { backStackEntry ->
                                val noteId = backStackEntry.arguments?.getString("noteId")
                                NotePage(navController = navController, noteId = noteId)
                            }
                            composable<NoteScreen> { NotePage(navController = navController) }
                        }
                    }
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
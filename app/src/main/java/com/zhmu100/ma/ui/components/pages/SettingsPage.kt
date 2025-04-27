package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.storage.SettingsStorage
import com.zhmu100.ma.domain.viewModel.SettingsViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.components.buttons.ToggleButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val currentTheme by viewModel.theme.collectAsState()
    val currentLanguage by viewModel.language.collectAsState()

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Настройки",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Изменить тему")
                ToggleButton(
                    initState = currentTheme == SettingsStorage.AppTheme.DARK,
                    onCheckedChange = { isDark ->
                        viewModel.setTheme(
                            if (isDark) SettingsStorage.AppTheme.DARK
                            else SettingsStorage.AppTheme.LIGHT
                        )
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Text("Изменить язык")
                StringButton(
                    text = currentLanguage.displayName,
                    onClick = {
                        val newLanguage = when (currentLanguage) {
                            SettingsStorage.AppLanguage.RUSSIAN ->
                                SettingsStorage.AppLanguage.ENGLISH

                            else -> SettingsStorage.AppLanguage.RUSSIAN
                        }
                        viewModel.setLanguage(newLanguage)
                    }
                )
            }
            Text(
                "ВЫЙТИ ИЗ АККАУНТА",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable { navController?.navigate(LoginScreen) })
        }
    }
}

@Serializable
object SettingsScreen

@Preview(showBackground = true)
@Composable
private fun SettingsPagePreview() {
    MATheme {
        SettingsPage()
    }
}
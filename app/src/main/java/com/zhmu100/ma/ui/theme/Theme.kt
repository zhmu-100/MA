package com.zhmu100.ma.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.zhmu100.ma.domain.storage.SettingsStorage
import com.zhmu100.ma.domain.viewModel.SettingsViewModel

/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/

// TODO dark theme
private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    inversePrimary = DarkGreen,
    secondary = LightBlue,
    tertiary = Orange,
    background = Black,
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    inversePrimary = DarkGreen,
    secondary = LightBlue,
    tertiary = Orange,
    background = White,
    surfaceVariant = LightGray,
)

@Composable
fun MATheme(
    settingsViewModel: SettingsViewModel? = null,
    content: @Composable () -> Unit
) {
    val theme = settingsViewModel?.theme?.collectAsState()

    val colorScheme = when (theme?.value) {
        SettingsStorage.AppTheme.DARK -> DarkColorScheme
        else -> LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = MATypography,
        content = content
    )
}
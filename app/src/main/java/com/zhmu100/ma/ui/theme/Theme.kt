package com.zhmu100.ma.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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
//    primary = Color.Black,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    inversePrimary = DarkGreen,
    secondary = LightBlue,
    tertiary = Orange,
    background = White,
)

@Composable
fun MATheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MATypography,
        content = content
    )
}
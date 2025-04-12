package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.components.buttons.ToggleButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun RemindersPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Напоминания",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            ReminderDivider()
            ReminderToggle(
                time = "8:00",
                isActive = true,
                text = "Покушать",
                onClick = {},
                onCheckedChange = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ReminderDivider()
            ReminderToggle(
                time = "9:00",
                isActive = false,
                text = "Опять покушать",
                onClick = {},
                onCheckedChange = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ReminderDivider()
            ThemedIconButton(
                imageVector = Icons.Default.Add,
                onClick = { navController?.navigate(ReminderScreen) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun ReminderToggle(
    time: String,
    isActive: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(time, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 56.sp)
            ToggleButton(initState = isActive, onCheckedChange = onCheckedChange)
        }
        Text(text, color = MaterialTheme.colorScheme.inversePrimary)
    }
}

@Composable
private fun ReminderDivider() {
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.inversePrimary)
}

@Serializable
object RemindersScreen


@Preview(showBackground = true)
@Composable
private fun RemindersPagePreview() {
    MATheme {
        RemindersPage()
    }
}
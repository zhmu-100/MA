package com.zhmu100.ma.ui.components.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.viewModel.NotificationViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.components.buttons.ToggleButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.zhmu100.ma.domain.model.notification.Notification
import com.zhmu100.ma.domain.viewModel.ViewState
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RemindersPage(modifier: Modifier = Modifier, navController: NavController? = null,
                  viewModel: NotificationViewModel = koinViewModel()
) {
    val notificationsState by viewModel.notificationsState.collectAsState()

    val currentTime = remember { mutableStateOf(LocalTime.now()) }
    val triggeredNotificationIds by viewModel.triggeredNotificationIds.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
        while (true) {
            val now = LocalTime.now()
            currentTime.value = now
            val notifications = (viewModel.notificationsState.value as? ViewState.Success<List<Notification>>)?.data.orEmpty()
            viewModel.updateTriggeredNotifications(now, notifications)
            Log.d(triggeredNotificationIds.toString(), "triiiiiiiger:${triggeredNotificationIds}")
            delay(1000)
        }
    }

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
            when (notificationsState) {
                is ViewState.Success -> {
                    val notifications = (notificationsState as ViewState.Success<List<Notification>>).data
                    notifications.forEach { notification ->
                        val shouldTrigger = isTimeToShowCircle(notification.time, currentTime.value) && notification.isActive

                        if (shouldTrigger && !triggeredNotificationIds.contains(notification.id)) {
                            viewModel.updateTriggeredNotifications(currentTime.value, notifications)
                        }

                        ReminderDivider()
                        ReminderToggle(
                            id = notification.id,
                            time = notification.time,
                            isActive = notification.isActive,
                            text = notification.text,
                            onClick = {
                                navController?.navigate("${ReminderScreen}/${notification.id}")
                            },
                            onCheckedChange = {}
                        )
                        if (shouldTrigger  && notification.isActive) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color.Red, shape = CircleShape)
                                    .align(Alignment.Start)
                            )
                        }
                    }
                }
                else -> {}
            }
            ThemedIconButton(
                imageVector = Icons.Default.Add,
                onClick = { navController?.navigate("${ReminderScreen}/") },
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
    onCheckedChange: (Boolean) -> Unit = {},
    id: String,
    viewModel: NotificationViewModel = koinViewModel()
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
            ToggleButton(
                initState = isActive,
                onCheckedChange = { newState ->
                    viewModel.toggleNotificationState(id, newState)
                    onCheckedChange(newState)
                }
                )
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

private fun isTimeToShowCircle(notificationTime: String, currentTime: LocalTime): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val notificationLocalTime = LocalTime.parse(notificationTime, formatter)

    return currentTime.isAfter(notificationLocalTime)
}

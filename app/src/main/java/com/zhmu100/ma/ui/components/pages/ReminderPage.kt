package com.zhmu100.ma.ui.components.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.viewModel.NotificationViewModel
import com.zhmu100.ma.domain.viewModel.ViewState
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.inputs.NumberSelector
import com.zhmu100.ma.ui.theme.LightGreenTransparent
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReminderPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: NotificationViewModel = koinViewModel(),
    reminderId: String?=null
    ) {

    val notificationState by viewModel.currentNotificationState.collectAsState()

    LaunchedEffect(reminderId) {
        reminderId?.let {
            viewModel.getNotification(reminderId)
        }
    }

    val notification = (notificationState as? ViewState.Success)?.data

    var reminderText by remember { mutableStateOf("Новое напоминание") }
    var selectedHour by remember { mutableStateOf(8) }
    var selectedMinute by remember { mutableStateOf(30) }

    LaunchedEffect(notification) {
        notification?.let {
            reminderText = it.text
            val (h, m) = it.time.split(":").map { it.toInt() }
            selectedHour = h
            selectedMinute = m
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
                    onClick = { navController?.navigate(RemindersScreen) })
                if (reminderId.isNullOrBlank()) {
                    SaveButton(
                        text = "Сохранить",
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            val time = "%02d:%02d".format(selectedHour, selectedMinute)
                            viewModel.createNotification(time, reminderText)
                            navController?.navigate(RemindersScreen)
                        }
                    )
                } else {
                    SaveButton(
                        text = "Изменить",
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            val time = "%02d:%02d".format(selectedHour, selectedMinute)
                            viewModel.updateNotification(reminderId, time, reminderText)
                            navController?.navigate(RemindersScreen)
                        }
                    )
                }
            }
            Text(
                "${selectedHour}:${selectedMinute}",
                fontSize = 56.sp,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            ReminderDivider()
            ReminderSelector(
                initialHour = selectedHour,
                initialMinute = selectedMinute,
                onHourChange = { selectedHour = it },
                onMinuteChange = { selectedMinute = it }
            )
            ReminderDivider()
            ReminderSettings(reminderText = reminderText, onReminderTextChange = { reminderText = it })
            SquareIconButton(
                imageVector = Icons.Default.Delete,
                onClick = {
                    reminderId?.let {
                        viewModel.deleteNotification(it)
                        navController?.navigate(RemindersScreen)
                    }
                }
                )
        }
    }
}

@Composable
private fun ReminderDivider() {
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.inversePrimary)
}

@Composable
fun ReminderSelector(
    initialHour: Int,
    initialMinute: Int,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(200.dp)
                .align(Alignment.Center)
        ) {
            NumberSelector(
                range = (0..23).toList(),
                selectedItem = initialHour,
                onItemSelected = onHourChange
            )
            NumberSelector(
                range = (0..59).toList(),
                selectedItem = initialMinute,
                onItemSelected = onMinuteChange
            )
        }
        HorizontalDivider(
            thickness = 30.dp,
            color = LightGreenTransparent,
            modifier = Modifier
                .align(Alignment.Center)
                .clip(shape = CircleShape)
        )
    }
}


@Composable
private fun ReminderSettings(
    reminderText: String,
    onReminderTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(25))
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Текст",
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.weight(1f)
            )
            println("text: ${reminderText}")
            BasicTextField(
                value = reminderText,
                onValueChange = onReminderTextChange,
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.background),
                decorationBox = { innerTextField ->
                    if (reminderText.isEmpty()) {
                        Text("Напоминание", color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    }
                    innerTextField()
                },
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Right Arrow", tint = White)
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Звук",
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.weight(1f)
            )
            Text("Radar", color = MaterialTheme.colorScheme.background)
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Right Arrow", tint = White)
        }
    }
}

@Serializable
object ReminderScreen


@Preview(showBackground = true)
@Composable
private fun ReminderPagePreview() {
    MATheme {
        ReminderPage()
    }
}
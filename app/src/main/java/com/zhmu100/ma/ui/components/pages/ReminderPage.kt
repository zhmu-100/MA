package com.zhmu100.ma.ui.components.pages

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.inputs.NumberSelector
import com.zhmu100.ma.ui.theme.LightGreenTransparent
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White
import kotlinx.serialization.Serializable

@Composable
fun ReminderPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            var selectedHour by remember { mutableIntStateOf(8) }
            var selectedMinute by remember { mutableIntStateOf(30) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(RemindersScreen) })
                SaveButton(text = "Сохранить", modifier = Modifier.align(Alignment.CenterEnd))
            }
            Text(
                "Изменить напоминание",
                fontWeight = FontWeight.Bold
            )
            Text(
                "${selectedHour}:${selectedMinute}",
                fontSize = 56.sp,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            ReminderDivider()
            ReminderSelector(8, 30, { selectedHour = it }, { selectedMinute = it })
            ReminderDivider()
            ReminderSettings()
            SquareIconButton(imageVector = Icons.Default.Delete)
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
private fun ReminderSettings() {
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
            Text("Напоминание", color = MaterialTheme.colorScheme.background)
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
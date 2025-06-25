package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.api.device.Device
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.model.device.MockHeartRateWatch
import com.zhmu100.ma.domain.viewModel.DevicesViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.SaveButton
import com.zhmu100.ma.ui.components.inputs.BorderlessInputLine
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun DevicePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DevicesViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("Новое устройство") }
    var selectedType by remember { mutableStateOf(DeviceType.WATCH) }
    val batteryLevel = remember { Random.nextInt(0, 101) }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Заголовок и кнопки
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                BackButton(text = "Назад", onClick = { navController?.navigate(DevicesScreen) })
                SaveButton(text = "Сохранить", onClick = {
                    val device = createDevice(name, selectedType, batteryLevel)
                    viewModel.addDevice(device)
                    navController?.navigateUp()
                })
            }
            Text(
                text = "Добавить устройство",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Имя устройства:",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            BorderlessInputLine(
                initValue = name,
                placeholder = "Введите имя устройства",
                onTextChanged = { name = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Выбор типа
            DeviceTypeSelector(selectedType) { newType ->
                selectedType = newType
            }
        }
    }
}

/**
 * Компонент для выбора типа устройства.
 */
@Composable
fun DeviceTypeSelector(
    selectedType: DeviceType,
    onTypeSelected: (DeviceType) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Тип устройства:",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row {
            for (type in DeviceType.entries) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = (selectedType == type),
                        onClick = { onTypeSelected(type) }
                    )
                    Text(text = type.typeName)
                }
            }
        }
    }
}

/**
 * Создает новое устройство на основе выбранного типа.
 */
fun createDevice(name: String, type: DeviceType, batteryLevel: Int): Device {
    return when (type) {
        DeviceType.WATCH -> MockHeartRateWatch(_name = name, batteryLevel = batteryLevel)
        DeviceType.SCALE -> MockHeartRateWatch(
            _name = name,
            batteryLevel = batteryLevel,
            type = DeviceType.SCALE
        )
    }
}

@Serializable
object DeviceScreen

@Preview(showBackground = true)
@Composable
private fun DevicePagePreview() {
    MATheme {
        DevicePage()
    }
}
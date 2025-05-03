package com.zhmu100.ma.ui.components.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.device.DeviceStatus
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.viewModel.DevicesViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.CategoryButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DevicesViewModel = koinViewModel()
) {
    var devicesInd by remember { mutableIntStateOf(0) }
    val filteredDevices by viewModel.filteredDevices.collectAsState()

    fun filter() {
        when (devicesInd) {
            0 -> viewModel.filterByType(null)
            1 -> viewModel.filterByType(DeviceType.WATCH)
            2 -> viewModel.filterByType(DeviceType.SCALE)
        }
    }

    LaunchedEffect(devicesInd) {
        filter()
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                BackButton(text = "Назад", onClick = { navController?.navigate(ProfileScreen) })
                Text("Устройства", Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold)
            }

            Row(modifier = Modifier.padding(vertical = 16.dp)) {
                CategoryButton("Все", isActive = devicesInd == 0, onClick = { devicesInd = 0 })
                CategoryButton("Часы", isActive = devicesInd == 1, onClick = { devicesInd = 1 })
                CategoryButton("Весы", isActive = devicesInd == 2, onClick = { devicesInd = 2 })
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredDevices.size) { index ->
                    val device = filteredDevices[index]
                    DeviceCard(
                        name = device.getName(),
                        status = device.getStatus().description,
                        battery = device.getBatteryLevel(),
                        type = device.getType().name,
                        isActive = device.getStatus() == DeviceStatus.ON,
                        deviceType = device.getType(),
                        onRemove = {
                            Log.d("DeviceCard", "Удаление устройства: ${device.getName()}")
                            viewModel.removeDevice(device)
                        }
                    )
                }
            }

            ThemedIconButton(Icons.Default.Add, onClick = { navController?.navigate(DeviceScreen) })
        }
    }
}

/**
 * Карточка устройства на экране устройств.
 *
 * @param name Имя устройства
 * @param status Статус устройства
 * @param battery Заряд устройства (в процентах)
 * @param type Тип устройства
 * @param isActive Активное ли устройство (подключено или нет)
 */
@Composable
fun DeviceCard(
    name: String,
    status: String,
    battery: Int,
    type: String,
    isActive: Boolean,
    deviceType: DeviceType,
    onRemove: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Левая часть: иконка + текст
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            // Мини-иконка устройства
            val iconResId = when (deviceType) {
                DeviceType.WATCH -> R.drawable.watch
                DeviceType.SCALE -> R.drawable.scale
            }

            SquareIconButton(
                iconResourceId = iconResId,
                background = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Текстовое описание
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Тип: $type", fontSize = 14.sp)
                Text("Статус: $status", fontSize = 14.sp)
                Text("Заряд: $battery%", fontSize = 14.sp)
            }

            // Кнопка удаления справа
            SquareIconButton(
                imageVector = Icons.Default.Delete,
                onClick = onRemove
            )
        }

    }
}


@Serializable
object DevicesScreen

@Preview(showBackground = true)
@Composable
private fun DevicesPagePreview() {
    MATheme {
        DevicesPage()
    }
}
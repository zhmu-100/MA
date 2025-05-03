package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.device.Device
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.storage.DeviceStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DevicesViewModel(
    private val deviceStorage: DeviceStorage
) : ViewModel() {

    // Фильтрованный список устройств
    private val _filteredDevices = MutableStateFlow<List<Device>>(emptyList())
    val filteredDevices: StateFlow<List<Device>> = _filteredDevices.asStateFlow()

    private var currentFilterType: DeviceType? = null
    private fun getCurrentFilterType(): DeviceType? = currentFilterType

    init {
        // Подписываемся на обновления списка устройств
        viewModelScope.launch {
            deviceStorage.devices.collect { devices ->
                // Применяем текущую фильтрацию
                val currentType = getCurrentFilterType()
                val filtered = if (currentType == null) {
                    devices
                } else {
                    devices.filter { it.getType() == currentType }
                }
                _filteredDevices.value = filtered
            }
        }
    }

    /**
     * Фильтрует устройства по типу и обновляет `_filteredDevices`.
     */
    fun filterByType(type: DeviceType?) {
        currentFilterType = type
        val filtered = deviceStorage.getDevicesByType(type)
        _filteredDevices.value = filtered
    }

    /**
     * Удаляет устройство и обновляет оба списка.
     */
    fun removeDevice(device: Device) {
        deviceStorage.removeDevice(device)
    }

    /**
     * Добавляет устройство и обновляет оба списка.
     */
    fun addDevice(device: Device) {
        deviceStorage.addDevice(device)
    }
}
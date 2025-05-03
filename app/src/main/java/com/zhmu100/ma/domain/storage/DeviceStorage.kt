package com.zhmu100.ma.domain.storage

import com.zhmu100.ma.domain.api.device.Device
import com.zhmu100.ma.domain.model.device.DeviceStatus
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.model.device.MockHeartRateWatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Хранилище для управления списком устройств.
 *
 * Позволяет:
 * - Получить текущий список устройств
 * - Добавлять новые устройства
 * - Удалять существующие
 * - Фильтровать по типу
 */
object DeviceStorage {
    /**
     * Текущий список мок-устройств.
     */
    private val mockDevices = mutableListOf<Device>()

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    init {
        mockDevices.addAll(
            listOf(
                MockHeartRateWatch(
                    _name = "Apple Watch",
                    batteryLevel = 75,
                    status = DeviceStatus.ON
                ),
                MockHeartRateWatch(
                    _name = "Samsung Watch",
                    batteryLevel = 20,
                    status = DeviceStatus.ON
                )
            )
        )
        _devices.value = mockDevices.toList()
    }

    /**
     * Список устройств, отфильтрованный по текущему типу.
     */
    fun getDevicesByType(type: DeviceType?): List<Device> {
        return if (type == null) mockDevices else mockDevices.filter { it.getType() == type }
    }

    /**
     * Добавить новое устройство в список.
     */
    fun addDevice(device: Device) {
        mockDevices.add(device)
        _devices.value = mockDevices.toList()
    }

    /**
     * Удалить устройство из списка.
     */
    fun removeDevice(device: Device) {
        mockDevices.remove(device)
        _devices.value = mockDevices.toList()
    }
}
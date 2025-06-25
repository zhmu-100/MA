package com.zhmu100.ma.domain.model.device

import com.zhmu100.ma.domain.api.device.Device
import java.time.Instant
import kotlin.random.Random

/**
 * Мок-устройство — умные часы, которые могут считывать пульс.
 *
 * @property _name начальное имя устройства
 * @property batteryLevel начальный уровень заряда
 */
class MockHeartRateWatch(
    private var _name: String = "Умные часы",
    private val type: DeviceType = DeviceType.WATCH,
    private var status: DeviceStatus = DeviceStatus.ON,
    private var batteryLevel: Int = 100
) : Device {

    override fun getName() = _name

    override fun setName(name: String) {
        this._name = name
    }

    override fun getType() = type

    override fun getStatus() = status

    override fun getBatteryLevel() = batteryLevel

    override fun connect() {
        status = DeviceStatus.CONNECTING
        // Имитация задержки при подключении
        Thread.sleep(1000)
        status = DeviceStatus.ON
        // При подключении немного снижаем заряд
        batteryLevel = maxOf(0, batteryLevel - 2)
    }

    override fun disconnect() {
        status = DeviceStatus.OFF
    }

    override fun getReading(): DeviceReading {
        if (status != DeviceStatus.ON) {
            throw IllegalStateException("Устройство должно быть включено для получения данных.")
        }
        // Имитируем пульс в диапазоне 60–100 ударов в минуту
        val heartRate = Random.nextDouble(60.0, 100.0)
        return DeviceReading(value = heartRate, timestamp = Instant.now())
    }
}
package com.zhmu100.ma.domain.api.device

import com.zhmu100.ma.domain.model.device.DeviceReading
import com.zhmu100.ma.domain.model.device.DeviceStatus
import com.zhmu100.ma.domain.model.device.DeviceType

/**
 * Базовый интерфейс для всех типов устройств.
 *
 * Каждое устройство имеет:
 * - имя (может быть изменено пользователем)
 * - тип устройства ([DeviceType])
 * - статус ([DeviceStatus])
 * - уровень заряда (от 0 до 100)
 * - возможность подключения/отключения
 * - возможность получения показаний
 */
interface Device {
    /**
     * Получить текущее имя устройства.
     *
     * @return строка, представляющая имя устройства
     */
    fun getName(): String

    /**
     * Задать новое имя устройства.
     *
     * @param name новое имя устройства
     */
    fun setName(name: String)

    /**
     * Получить тип устройства.
     *
     * @return [DeviceType], указывающий на категорию устройства
     */
    fun getType(): DeviceType

    /**
     * Получить текущий статус устройства.
     *
     * @return [DeviceStatus], отражающий состояние устройства
     */
    fun getStatus(): DeviceStatus

    /**
     * Получить уровень заряда устройства.
     *
     * @return значение от 0 до 100, где 0 — разряжено, 100 — полностью заряжено
     */
    fun getBatteryLevel(): Int

    /**
     * Подключить устройство.
     *
     * Меняет статус на [DeviceStatus.CONNECTING] и имитирует процесс подключения.
     */
    fun connect()

    /**
     * Отключить устройство.
     *
     * Меняет статус на [DeviceStatus.OFF].
     */
    fun disconnect()

    /**
     * Получить текущее показание с устройства.
     *
     * @return [DeviceReading] с данными, считанными с устройства
     * @throws IllegalStateException если устройство не подключено
     */
    fun getReading(): DeviceReading
}
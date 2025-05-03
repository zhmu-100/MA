package com.zhmu100.ma.domain.model.device

import java.time.Instant

/**
 * Данные, считываемые с устройства.
 *
 * @property value числовое значение, полученное с устройства (например, пульс или вес)
 * @property timestamp время, когда были получены данные
 */
data class DeviceReading(
    val value: Double,
    val timestamp: Instant
)
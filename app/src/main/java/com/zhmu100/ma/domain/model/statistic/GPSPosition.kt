package com.zhmu100.ma.domain.model.statistic

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

/**
 * Данные GPS позиции
 * @property timestamp Временная метка в формате ISO-8601
 * @property latitude Широта
 * @property longitude Долгота
 * @property altitude Высота над уровнем моря
 * @property speed Скорость в м/с
 * @property accuracy Точность измерения в метрах
 */
@Serializable
data class GPSPosition(
    val timestamp: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val speed: Double,
    val accuracy: Double
) {
    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}
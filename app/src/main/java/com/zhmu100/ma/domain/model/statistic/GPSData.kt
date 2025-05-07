package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Данные GPS
 * @property meta Метаданные упражнения
 * @property positions Список GPS позиций
 */
@Serializable
data class GPSData(
    val meta: ExerciseMetadata,
    val positions: List<GPSPosition> = emptyList()
)

@Serializable
data class GPSDataResponse(
    val gps_data: List<GPSData>
)

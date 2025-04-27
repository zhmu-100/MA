package com.zhmu100.ma.domain.model.statistic

/**
 * Данные GPS
 * @property meta Метаданные упражнения
 * @property positions Список GPS позиций
 */
data class GPSData(
    val meta: ExerciseMetadata,
    val positions: List<GPSPosition> = emptyList()
)
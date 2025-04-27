package com.zhmu100.ma.domain.model.statistic

/**
 * Данные сердечного ритма
 * @property meta Метаданные упражнения
 * @property bpm Ударов в минуту
 */
data class HeartRateData(
    val meta: ExerciseMetadata,
    val bpm: Int
)
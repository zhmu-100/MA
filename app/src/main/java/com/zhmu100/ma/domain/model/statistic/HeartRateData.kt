package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

/**
 * Данные сердечного ритма
 * @property meta Метаданные упражнения
 * @property bpm Ударов в минуту
 */
@Serializable
data class HeartRateData(
    val meta: ExerciseMetadata,
    val bpm: Int
)
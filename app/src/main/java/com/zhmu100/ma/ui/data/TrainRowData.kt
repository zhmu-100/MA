package com.zhmu100.ma.ui.data

import com.zhmu100.ma.domain.model.training.ExerciseName

/**
 * Данные строки тренировки
 *
 * @property exercise Название упражнения
 * @property value Значение (количество повторений/вес/время)
 */
data class TrainRowData(
    val exercise: ExerciseName,
    val value: String
)
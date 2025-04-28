package com.zhmu100.ma.ui.data

import com.zhmu100.ma.domain.model.training.ExerciseReaction

/**
 * Опция настроения для выбора после тренировки
 *
 * @property iconId Идентификатор ресурса иконки
 * @property description Текстовое описание настроения
 * @property emotion Тип реакции на упражнение
 */
data class MoodOption(
    val iconId: Int,
    val description: String,
    val emotion: ExerciseReaction
)
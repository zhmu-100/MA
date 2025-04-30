package com.zhmu100.ma.ui.data

import com.zhmu100.ma.R
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
) {
    companion object {
        fun getByReaction(reaction: ExerciseReaction): MoodOption {
            return when (reaction) {
                ExerciseReaction.VERY_BAD -> moods[0]
                ExerciseReaction.BAD -> moods[1]
                ExerciseReaction.OK -> moods[2]
                ExerciseReaction.GOOD -> moods[3]
                ExerciseReaction.EXCELLENT -> moods[4]
                ExerciseReaction.UNSPECIFIED -> moods[2]
            }
        }
    }
}

private val moods = listOf(
    MoodOption(R.drawable.sentiment_very_dissatisfied, "Ужасно", ExerciseReaction.VERY_BAD),
    MoodOption(R.drawable.sentiment_dissatisfied, "Плохо", ExerciseReaction.BAD),
    MoodOption(R.drawable.sentiment_neutral, "Нормально", ExerciseReaction.OK),
    MoodOption(R.drawable.sentiment_satisfied, "Хорошо", ExerciseReaction.GOOD),
    MoodOption(R.drawable.sentiment_very_satisfied, "Восхитительно", ExerciseReaction.EXCELLENT)
)
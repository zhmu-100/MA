package com.zhmu100.ma.ui.data

import com.zhmu100.ma.domain.model.training.ExerciseReaction

data class MoodOption(
    val iconId: Int,
    val description: String,
    val emotion: ExerciseReaction
)
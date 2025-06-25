package com.zhmu100.ma.domain.model.statistic

import kotlinx.serialization.Serializable

@Serializable
data class NutritionDay(
    val calories: Int,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fats: Double = 0.0
)
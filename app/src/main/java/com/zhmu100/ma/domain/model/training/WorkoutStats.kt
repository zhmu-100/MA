package com.zhmu100.ma.domain.model.training

import java.time.Duration

data class WorkoutStats(
    val totalDistance: Double,
    val duration: Duration,
    val steps: Int,
    val calories: Int
)

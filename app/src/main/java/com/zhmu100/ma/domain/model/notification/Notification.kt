package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String,
    val userId: String,
    val time: String, // Формат "HH:mm"
    val text: String,
    val isActive: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)
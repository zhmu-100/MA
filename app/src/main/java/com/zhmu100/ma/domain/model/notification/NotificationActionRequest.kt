package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationActionRequest(
    val id: String,
    val userId: String,
    val action: NotificationAction,
    val snoozeDuration: NotificationSnooze? = null
)
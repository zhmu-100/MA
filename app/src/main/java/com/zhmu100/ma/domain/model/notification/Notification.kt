package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String? = null,
    val userId: String?= null,
    val title: String? = null,
    val description: String? = null,
    val createDate: String? = null,
    val notificationDate: String? = null
)
package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class ListNotificationsResponse(
    val notifications: List<Notification>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
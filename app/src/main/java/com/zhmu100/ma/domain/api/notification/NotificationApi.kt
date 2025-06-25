package com.zhmu100.ma.domain.api.notification

import com.zhmu100.ma.domain.model.notification.ListNotificationsResponse
import com.zhmu100.ma.domain.model.notification.Notification
import com.zhmu100.ma.domain.model.notification.NotificationActionRequest

interface NotificationApi {
    suspend fun createNotification(notification: Notification): Notification
    suspend fun getNotification(id: String): Notification
    suspend fun listNotifications(userId: String, page: Int, pageSize: Int): ListNotificationsResponse
    suspend fun performNotificationAction(request: NotificationActionRequest): Notification
}
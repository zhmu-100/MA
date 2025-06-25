package com.zhmu100.ma.domain.api.notification

import com.zhmu100.ma.domain.model.notification.ListNotificationsResponse
import com.zhmu100.ma.domain.model.notification.Notification
import com.zhmu100.ma.domain.model.notification.NotificationActionRequest
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class NotificationApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : NotificationApi {
    override suspend fun createNotification(notification: Notification): Notification {
        return client.post("$baseUrl/notifications") {
            contentType(ContentType.Application.Json)
            setBody(notification)
        }.body()
    }

    override suspend fun getNotification(id: String): Notification {
        return client.get("$baseUrl/notifications/$id").body()
    }

    override suspend fun listNotifications(
        userId: String,
        page: Int,
        pageSize: Int
    ): ListNotificationsResponse {
        return client.get("$baseUrl/notifications") {
            parameter("user_id", userId)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    override suspend fun performNotificationAction(request: NotificationActionRequest): Notification {
        return client.post("$baseUrl/notifications/${request.id}/action") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
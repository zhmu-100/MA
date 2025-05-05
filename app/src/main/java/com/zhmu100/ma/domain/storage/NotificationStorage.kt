package com.zhmu100.ma.domain.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONObject
import com.zhmu100.ma.domain.model.notification.Notification

object NotificationStorage {
    private const val PREFS_NAME = "notifications_prefs"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveNotifications(userId: String, notifications: List<Notification>) {
        val jsonArray = JSONArray()
        notifications.forEach { notification ->
            jsonArray.put(JSONObject().apply {
                put("id", notification.id)
                put("userId", notification.userId)
                put("time", notification.time)
                put("text", notification.text)
                put("isActive", notification.isActive)
                put("createdAt", notification.createdAt)
            })
        }

        sharedPreferences.edit {
            putString("notifications_$userId", jsonArray.toString())
        }
    }

    fun getNotifications(userId: String): List<Notification> {
        val jsonString = sharedPreferences.getString("notifications_$userId", null) ?: return emptyList()
        val jsonArray = JSONArray(jsonString)
        val notifications = mutableListOf<Notification>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            notifications.add(Notification(
                id = jsonObject.getString("id"),
                userId = jsonObject.getString("userId"),
                time = jsonObject.getString("time"),
                text = jsonObject.getString("text"),
                isActive = jsonObject.getBoolean("isActive"),
                createdAt = jsonObject.getLong("createdAt")
            ))
        }

        return notifications
    }

    fun getNotification(userId: String, id: String?): Notification? {
        return getNotifications(userId).firstOrNull { it.id == id }
    }

    fun addNotification(userId: String, notification: Notification) {
        val current = getNotifications(userId).toMutableList()
        current.add(notification)
        saveNotifications(userId, current)
    }

    fun updateNotification(userId: String, notification: Notification) {
        val current = getNotifications(userId).toMutableList()
        val index = current.indexOfFirst { it.id == notification.id }
        if (index != -1) {
            current[index] = notification
            saveNotifications(userId, current)
        }
    }

    fun deleteNotification(userId: String, id: String) {
        val current = getNotifications(userId).toMutableList()
        current.removeIf { it.id == id }
        saveNotifications(userId, current)
    }
}
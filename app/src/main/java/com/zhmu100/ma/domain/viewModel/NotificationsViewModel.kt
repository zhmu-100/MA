package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.model.notification.Notification
import com.zhmu100.ma.domain.storage.NotificationStorage
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class NotificationViewModel(
    private val notificationStorage: NotificationStorage,
    tokenStorage: TokenStorage
) : ViewModel() {
    private val _notificationsState = MutableStateFlow<ViewState<List<Notification>>>(ViewState.Uninitialized)
    val notificationsState = _notificationsState.asStateFlow()

    private val _currentNotificationState = MutableStateFlow<ViewState<Notification>>(ViewState.Uninitialized)
    val currentNotificationState = _currentNotificationState.asStateFlow()

    private var userId: String = tokenStorage.getUserId()

    private val _triggeredNotificationIds = MutableStateFlow<Set<String>>(emptySet())
    val triggeredNotificationIds = _triggeredNotificationIds.asStateFlow()

    fun updateTriggeredNotifications(currentTime: LocalTime, notifications: List<Notification>) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val triggered = notifications
            .filter {
                val notifTime = LocalTime.parse(it.time, formatter)
                notifTime.isBefore(currentTime) && it.isActive
            }
            .map { it.id }
            .toSet()

        _triggeredNotificationIds.value = triggered
    }

    fun loadNotifications(forceRefresh: Boolean = false) {

        if (_notificationsState.value is ViewState.Loading && !forceRefresh) return
        _notificationsState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                val notifications = notificationStorage.getNotifications(userId)
                _notificationsState.value = ViewState.Success(notifications)
            } catch (e: Exception) {
                _notificationsState.value = ViewState.Error("Error loaded: ${e.message}", e)
            }
        }
    }

    fun getNotification(id: String?) {
        if (_currentNotificationState.value is ViewState.Loading) return
        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                val notification = notificationStorage.getNotification(userId, id)
                    ?: throw Exception("Notification not found")
                _currentNotificationState.value = ViewState.Success(notification)
            } catch (e: Exception) {
                _currentNotificationState.value = ViewState.Error("Error to get notification: ${e.message}", e)
            }
        }
    }

    fun createNotification(time: String, reminderText: String) {
        if (_currentNotificationState.value is ViewState.Loading) return
        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                val newNotification = Notification(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    time = time,
                    text = reminderText,
                    isActive = true
                )
                notificationStorage.addNotification(userId, newNotification)
                _currentNotificationState.value = ViewState.Success(newNotification, "Notification created")
                loadNotifications(true)
            } catch (e: Exception) {
                _currentNotificationState.value = ViewState.Error("Error to create notification: ${e.message}", e)
            }
        }
    }

    fun updateNotification(id: String, time: String, reminderText: String) {
        if (_currentNotificationState.value is ViewState.Loading) return
        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                val updateNotification = Notification(
                    id = id,
                    userId = userId,
                    time = time,
                    text = reminderText,
                    isActive = true
                )
                notificationStorage.updateNotification(userId, updateNotification)
                _currentNotificationState.value = ViewState.Success(updateNotification, "Notification updated")
                loadNotifications(true)
            } catch (e: Exception) {
                _currentNotificationState.value = ViewState.Error("Error to update notification: ${e.message}", e)
            }
        }
    }

    fun deleteNotification(id: String) {
        if (_currentNotificationState.value is ViewState.Loading) return
        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                notificationStorage.deleteNotification(userId, id)
                loadNotifications(true)
            } catch (e: Exception) {
                _currentNotificationState.value = ViewState.Error("Error to delete notification: ${e.message}", e)
            }
        }
    }

    fun toggleNotificationState(id: String, isActive: Boolean) {
        if (_currentNotificationState.value is ViewState.Loading) return
        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            try {
                val notification = notificationStorage.getNotification(userId, id)
                    ?: throw Exception("Notification not found")
                val updatedNotification = notification.copy(isActive = isActive)
                notificationStorage.updateNotification(userId, updatedNotification)
                _currentNotificationState.value = ViewState.Success(updatedNotification, "Notification state updated")
                loadNotifications(true)

            } catch (e: Exception) {
                _currentNotificationState.value = ViewState.Error("Error updating notification state: ${e.message}", e)
            }
        }
    }


    fun clearErrors() {
        _notificationsState.value = ViewState.Uninitialized
        _currentNotificationState.value = ViewState.Uninitialized
    }
}
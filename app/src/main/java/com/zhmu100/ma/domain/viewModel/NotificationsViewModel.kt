package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.notification.NotificationApi
import com.zhmu100.ma.domain.model.notification.Notification
import com.zhmu100.ma.domain.model.notification.NotificationActionRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationApi: NotificationApi
) : ViewModel() {
    private val _notificationsState = MutableStateFlow<ViewState<List<Notification>>>(ViewState.Uninitialized)
    val notificationsState = _notificationsState.asStateFlow()

    private val _currentNotificationState = MutableStateFlow<ViewState<Notification>>(ViewState.Uninitialized)
    val currentNotificationState = _currentNotificationState.asStateFlow()

    fun loadNotifications(userId: String, page: Int = 1, pageSize: Int = 10, forceRefresh: Boolean = false) {
        if (_notificationsState.value is ViewState.Loading && !forceRefresh) return

        _notificationsState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                notificationApi.listNotifications(userId, page, pageSize).notifications
            }.onSuccess { notifications ->
                _notificationsState.value = ViewState.Success(notifications)
            }.onFailure {
                _notificationsState.value = ViewState.Error("Error loaded: ${it.message}", it)
            }
        }
    }

    fun getNotification(id: String) {
        if (_currentNotificationState.value is ViewState.Loading) return

        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                notificationApi.getNotification(id)
            }.onSuccess { notification ->
                _currentNotificationState.value = ViewState.Success(notification)
            }.onFailure {
                _currentNotificationState.value = ViewState.Error("Error to get notification: ${it.message}", it)
            }
        }
    }

    fun createNotification(notification: Notification) {
        if (_currentNotificationState.value is ViewState.Loading) return

        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                notificationApi.createNotification(notification)
            }.onSuccess { createdNotification ->
                _currentNotificationState.value = ViewState.Success(createdNotification, "Notification created")
            }.onFailure {
                _currentNotificationState.value = ViewState.Error("Error to create notification: ${it.message}", it)
            }
        }
    }

    fun performNotificationAction(request: NotificationActionRequest) {
        if (_currentNotificationState.value is ViewState.Loading) return

        _currentNotificationState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                notificationApi.performNotificationAction(request)
            }.onSuccess { updatedNotification ->
                _currentNotificationState.value = ViewState.Success(updatedNotification, "Action performed")
            }.onFailure {
                _currentNotificationState.value = ViewState.Error("Error to perform action: ${it.message}", it)
            }
        }
    }

    fun clearErrors() {
        _notificationsState.value = ViewState.Uninitialized
        _currentNotificationState.value = ViewState.Uninitialized
    }
}
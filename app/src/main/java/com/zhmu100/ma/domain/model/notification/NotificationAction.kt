package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
enum class NotificationAction {
    UNSPECIFIED,
    COMPLETE,
    DISMISS,
    SNOOZE
}
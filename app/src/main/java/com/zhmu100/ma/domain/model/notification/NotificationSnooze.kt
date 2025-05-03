package com.zhmu100.ma.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
enum class NotificationSnooze {
    UNSPECIFIED,
    FIVE_MINUTES,
    FIFTEEN_MINUTES,
    THIRTY_MINUTES,
    ONE_HOUR,
    FIVE_HOURS,
    ONE_DAY
}
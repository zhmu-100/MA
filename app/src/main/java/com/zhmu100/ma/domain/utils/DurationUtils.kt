package com.zhmu100.ma.domain.utils

import java.time.Duration
import java.time.format.DateTimeParseException

object DurationUtils {

    // Преобразование Duration в строку ISO-8601
    fun durationToIsoString(duration: Duration): String {
        return duration.toString()
    }

    // Преобразование строки ISO-8601 в Duration
    fun isoStringToDuration(isoString: String): Duration? {
        return try {
            Duration.parse(isoString)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    // Дополнительно: форматирование Duration в удобочитаемый вид
    fun formatDuration(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    // Дополнительно: создание Duration из часов, минут, секунд
    fun createDuration(hours: Long = 0, minutes: Long = 0, seconds: Long = 0): Duration {
        return Duration.ofHours(hours)
            .plusMinutes(minutes)
            .plusSeconds(seconds)
    }
}
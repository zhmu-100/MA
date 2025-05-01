package com.zhmu100.ma.domain.utils

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar

object DateTimeUtils {

    // Форматтер для ISO-8601 даты
    private val isoFormatter = DateTimeFormatter.ISO_INSTANT

    // Преобразование Instant в строку ISO-8601
    fun instantToIsoString(instant: Instant): String {
        return isoFormatter.format(instant)
    }

    // Преобразование строки ISO-8601 в Instant
    fun isoStringToInstant(isoString: String): Instant? {
        return try {
            Instant.parse(isoString)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    // Дополнительно: преобразование в локальное время
    fun instantToLocalDateTime(instant: Instant, zoneId: ZoneId = ZoneId.systemDefault()): String {
        return ZonedDateTime.ofInstant(instant, zoneId)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun getMondayOfThisWeek(): LocalDate {
        val now = LocalDate.now()
        return now.minusDays((now.dayOfWeek.value - DayOfWeek.MONDAY.value).toLong())
    }

    fun getSevenDaysFrom(start: LocalDate): List<LocalDate> {
        return (0..6).map { start.plusDays(it.toLong()) }
    }

    fun String.isInDay(date: LocalDate): Boolean {
        val instant = Instant.parse(this)

        // Старый стиль через Date и Calendar
        val cal = Calendar.getInstance()
        cal.timeInMillis = instant.toEpochMilli()

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) // 0-based
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val calendarDate = Calendar.getInstance().apply {
            set(year, month, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return calendarDate.timeInMillis == date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
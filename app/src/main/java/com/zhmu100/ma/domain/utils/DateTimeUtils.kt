package com.zhmu100.ma.domain.utils

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
        val itemDate = LocalDate.ofInstant(instant, ZoneId.systemDefault())
        return itemDate == date
    }
}
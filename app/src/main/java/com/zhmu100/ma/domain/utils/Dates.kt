package com.zhmu100.ma.domain.utils

import java.util.Calendar

/**
 * Вычисляет возраст по году рождения
 *
 * @param birthYear Год рождения
 * @return Возраст в годах
 */
fun calculateAge(birthYear: Int): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return currentYear - birthYear
}

/**
 * Вычисляет год рождения по возрасту
 *
 * @param age Возраст в годах
 * @return Год рождения
 */
fun calculateYear(age: Int): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return currentYear - age
}
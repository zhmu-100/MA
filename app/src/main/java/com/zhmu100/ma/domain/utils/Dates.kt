package com.zhmu100.ma.domain.utils

import java.util.Calendar

fun calculateAge(birthYear: Int): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return currentYear - birthYear
}

fun calculateYear(age: Int): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return currentYear - age
}
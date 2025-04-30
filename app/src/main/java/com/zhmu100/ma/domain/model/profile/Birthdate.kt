package com.zhmu100.ma.domain.model.profile

/**
 * Класс, представляющий дату рождения пользователя.
 *
 * @property year Год рождения.
 * @property month Месяц рождения (1-12).
 * @property day День рождения (1-31).
 */
data class Birthdate(val year: Int, val month: Int, val day: Int)
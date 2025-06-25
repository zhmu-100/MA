package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Класс, представляющий местоположение пользователя.
 *
 * @property country Страна проживания пользователя.
 * @property city Город проживания пользователя.
 */
@Serializable
data class Location(val country: String, val city: String)
package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Основной класс профиля пользователя.
 *
 * @property id Уникальный идентификатор пользователя.
 * @property name Имя пользователя.
 * @property email Электронная почта пользователя.
 * @property imageId Идентификатор изображения профиля (может быть null).
 * @property bio Пол пользователя "M" | "F" (может быть null).
 * @property location Местоположение пользователя (может быть null).
 * @property birthdate Дата рождения пользователя (может быть null).
 * @property weight Вес пользователя в кг (может быть null).
 * @property height Рост пользователя в см (может быть null).
 * @property followerCount Количество подписчиков пользователя (по умолчанию 0).
 * @property followingCount Количество пользователей, на которых подписан данный пользователь (по умолчанию 0).
 */
@Serializable
data class UserProfile(
    val id: String,
    val user_id: String? = null,
    val name: String,
    val email: String,
    @Serializable
    val birthdate: Birthdate? = Birthdate(1990, 10, 5),
    val imageId: String? = null,
    val bio: String? = null,
    val location: Location? = null,
    val weight: Double? = null,
    val height: Double? = null,
    val followerCount: Int = 0,
    val followingCount: Int = 0
)
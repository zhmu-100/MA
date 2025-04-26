package com.zhmu100.ma.domain.model.profile

/**
 * Ответ на запрос списка профилей с пагинацией.
 *
 * @property profiles Список профилей пользователей.
 * @property total Общее количество профилей.
 * @property page Текущая страница.
 * @property pageSize Количество профилей на странице.
 */
data class ListProfilesResponse(
    val profiles: List<UserProfile>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
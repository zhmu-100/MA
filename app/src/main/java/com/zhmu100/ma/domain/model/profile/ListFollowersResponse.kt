package com.zhmu100.ma.domain.model.profile

/**
 * Ответ на запрос списка подписчиков с пагинацией.
 *
 * @property followerIds Список ID подписчиков.
 * @property total Общее количество подписчиков.
 * @property page Текущая страница.
 * @property pageSize Количество ID на странице.
 */
data class ListFollowersResponse(
    val followerIds: List<String>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
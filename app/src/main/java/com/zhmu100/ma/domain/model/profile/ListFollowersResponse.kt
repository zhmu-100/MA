package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Ответ на запрос списка подписчиков с пагинацией.
 *
 * @property followerIds Список ID подписчиков.
 * @property total Общее количество подписчиков.
 * @property page Текущая страница.
 * @property pageSize Количество ID на странице.
 */
@Serializable
data class ListFollowersResponse(
    val followerIds: List<String>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
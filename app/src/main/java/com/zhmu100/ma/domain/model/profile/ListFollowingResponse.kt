package com.zhmu100.ma.domain.model.profile

/**
 * Ответ на запрос списка подписок с пагинацией.
 *
 * @property followingIds Список ID пользователей, на которых подписан текущий пользователь.
 * @property total Общее количество подписок.
 * @property page Текущая страница.
 * @property pageSize Количество ID на странице.
 */
data class ListFollowingResponse(
    val followingIds: List<String>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
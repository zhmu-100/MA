package com.zhmu100.ma.domain.model.training

import kotlinx.serialization.Serializable

/**
 * Ответ на запрос списка тренировок с пагинацией
 *
 * @property workouts Список тренировок
 * @property total Общее количество тренировок
 * @property page Текущая страница
 * @property pageSize Количество элементов на странице
 */
@Serializable
data class ListWorkoutsResponse(
    val workouts: List<Workout>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
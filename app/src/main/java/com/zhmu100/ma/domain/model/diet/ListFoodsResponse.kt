package com.zhmu100.ma.domain.model.diet

import kotlinx.serialization.Serializable

/**
 * Ответ на запрос списка продуктов.
 *
 * @property foods Список продуктов
 * @property total Общее количество продуктов
 * @property page Номер текущей страницы
 * @property pageSize Количество продуктов на странице
 *
 * Используется для пагинации и фильтрации.
 */
@Serializable
data class ListFoodsResponse(
    val foods: List<Food>,
//    val total: Int,
//    val page: Int,
//    val pageSize: Int
)
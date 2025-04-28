package com.zhmu100.ma.domain.model.diet

/**
 * Ответ на запрос списка приемов пищи.
 *
 * @property meals Список приемов пищи
 * @property total Общее количество приемов пищи
 * @property page Номер текущей страницы
 * @property pageSize Количество приемов пищи на странице
 *
 * Используется для пагинации и фильтрации по дате.
 */
data class ListMealsResponse(
    val meals: List<Meal>,
    val total: Int,
    val page: Int,
    val pageSize: Int
)
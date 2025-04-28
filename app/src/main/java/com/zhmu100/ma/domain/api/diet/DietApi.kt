package com.zhmu100.ma.domain.api.diet

import com.zhmu100.ma.domain.model.diet.Food
import com.zhmu100.ma.domain.model.diet.ListFoodsResponse
import com.zhmu100.ma.domain.model.diet.ListMealsResponse
import com.zhmu100.ma.domain.model.diet.Meal

/**
 * API для общения с сервисом еды
 */
interface DietApi {
    /**
     * Получить продукт по id
     */
    suspend fun getFood(id: String): Food

    /**
     * Получить список продуктов, возможен фильтр по имени
     */
    suspend fun listFoods(nameFilter: String? = null): ListFoodsResponse

    /**
     * Создать прием продукта
     */
    suspend fun createFood(food: Food): Food

    /**
     * Получить прием пищи
     */
    suspend fun getMeal(id: String): Meal

    /**
     * Получить список приемов пищи за период
     */
    suspend fun listMeals(startDate: String, endDate: String): ListMealsResponse

    /**
     * Создать прием пищи
     */
    suspend fun createMeal(meal: Meal): Meal
}
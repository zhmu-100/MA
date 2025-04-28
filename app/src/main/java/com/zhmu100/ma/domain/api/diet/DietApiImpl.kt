package com.zhmu100.ma.domain.api.diet

import com.zhmu100.ma.domain.model.diet.Food
import com.zhmu100.ma.domain.model.diet.ListFoodsResponse
import com.zhmu100.ma.domain.model.diet.ListMealsResponse
import com.zhmu100.ma.domain.model.diet.Meal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DietApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : DietApi {

    override suspend fun getFood(id: String): Food {
        return client.get("$baseUrl/foods/$id").body()
    }

    override suspend fun listFoods(nameFilter: String?): ListFoodsResponse {
        return client.get("$baseUrl/foods") {
            nameFilter?.let { parameter("nameFilter", it) }
        }.body()
    }

    override suspend fun createFood(food: Food): Food {
        return client.post("$baseUrl/foods") {
            contentType(ContentType.Application.Json)
            setBody(food)
        }.body()
    }

    override suspend fun getMeal(id: String): Meal {
        return client.get("$baseUrl/meals/$id").body()
    }

    override suspend fun listMeals(startDate: String, endDate: String): ListMealsResponse {
        return client.get("$baseUrl/meals") {
            parameter("startDate", startDate)
            parameter("endDate", endDate)
        }.body()
    }

    override suspend fun createMeal(meal: Meal): Meal {
        return client.post("$baseUrl/meals") {
            contentType(ContentType.Application.Json)
            setBody(meal)
        }.body()
    }
}
package com.zhmu100.ma.domain.api.diet

import com.zhmu100.ma.domain.model.diet.Food
import com.zhmu100.ma.domain.model.diet.ListFoodsResponse
import com.zhmu100.ma.domain.model.diet.ListMealsResponse
import com.zhmu100.ma.domain.model.diet.Meal
import com.zhmu100.ma.domain.model.diet.MealType
import com.zhmu100.ma.domain.model.diet.Mineral
import java.time.LocalDate

class DietApiMock : DietApi {
    private val foods = mutableListOf<Food>()
    private val meals = mutableListOf<Meal>()

    init {
        // Инициализация с некоторыми тестовыми данными
        foods.add(
            Food(
                id = "1",
                name = "Apple",
                description = "A sweet red fruit",
                calories = 52.0,
                protein = 0.3,
                carbs = 14.0,
                saturatedFats = 0.0,
                transFats = 0.0,
                fiber = 2.4,
                sugar = 10.4,
                minerals = listOf(Mineral("mineral1", "A", 100.0, "г"))
            )
        )
        foods.add(
            Food(
                id = "2",
                name = "Banana",
                description = "A long yellow fruit",
                calories = 89.0,
                protein = 1.1,
                carbs = 23.0,
                saturatedFats = 0.3,
                transFats = 0.0,
                fiber = 2.6,
                sugar = 12.2
            )
        )

        meals.add(
            Meal(
                id = "1",
                name = "Breakfast",
                mealType = MealType.BREAKFAST,
                foods = listOf(foods[0]),
                date = "2025-05-01T08:00:00Z"
            )
        )
        meals.add(
            Meal(
                id = "2",
                name = "Lunch",
                mealType = MealType.LUNCH,
                foods = listOf(foods[1]),
                date = "2025-05-03T12:00:00Z"
            )
        )
    }

    override suspend fun getFood(id: String): Food {
        return foods.find { it.id == id } ?: throw NoSuchElementException("Food not found")
    }

    override suspend fun listFoods(nameFilter: String?): ListFoodsResponse {
        val filteredFoods = if (nameFilter != null) {
            foods.filter { it.name.contains(nameFilter, ignoreCase = true) }
        } else {
            foods
        }
        return ListFoodsResponse(
            foods = filteredFoods,
            total = filteredFoods.size,
            page = 1,
            pageSize = filteredFoods.size
        )
    }

    override suspend fun createFood(food: Food): Food {
        val newFood = food.copy(id = (foods.size + 1).toString())
        foods.add(newFood)
        return newFood
    }

    override suspend fun getMeal(id: String): Meal {
        return meals.find { it.id == id } ?: throw NoSuchElementException("Meal not found")
    }

    override suspend fun listMeals(startDate: String, endDate: String): ListMealsResponse {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)

        val filteredMeals = meals.filter { meal ->
            val mealDate =
                LocalDate.parse(meal.date.substringBefore("T")) // отбрасываем время, если есть
            mealDate in start..(end)
        }

        return ListMealsResponse(
            meals = filteredMeals,
            total = filteredMeals.size,
            page = 1,
            pageSize = filteredMeals.size
        )
    }

    override suspend fun createMeal(meal: Meal): Meal {
        val newMeal = meal.copy(id = (meals.size + 1).toString())
        meals.add(newMeal)
        return newMeal
    }
}
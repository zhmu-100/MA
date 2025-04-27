package com.zhmu100.ma.domain.api.training

import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.Workout
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TrainingApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : TrainingApi {

    override suspend fun getWorkout(id: String): Workout {
        return client.get("$baseUrl/workouts/$id").body()
    }

    override suspend fun listWorkouts(page: Int, pageSize: Int): List<Workout> {
        return client.get("$baseUrl/workouts") {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    override suspend fun getWorkoutExercises(workoutId: String): List<Exercise> {
        return client.get("$baseUrl/workouts/$workoutId/exercises").body()
    }

    override suspend fun createWorkout(workout: Workout): Workout {
        return client.post("$baseUrl/workouts") {
            contentType(ContentType.Application.Json)
            setBody(workout)
        }.body()
    }

    override suspend fun updateWorkout(id: String, workout: Workout): Workout {
        return client.put("$baseUrl/workouts/$id") {
            contentType(ContentType.Application.Json)
            setBody(workout)
        }.body()
    }

    override suspend fun deleteWorkout(id: String) {
        client.delete("$baseUrl/workouts/$id")
    }

    override suspend fun createCustomWorkout(workout: Workout): String {
        return client.post("$baseUrl/workouts/custom") {
            contentType(ContentType.Application.Json)
            setBody(workout)
        }.body()
    }
}
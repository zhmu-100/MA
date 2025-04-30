package com.zhmu100.ma.domain.api.training

import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.Workout

/**
 * API для общения с сервисом профиля
 */
interface TrainingApi {
    /**
     * Получить тренировку по ID
     */
    suspend fun getWorkout(id: String): Workout

    /**
     * Получить список своих тренировок с пагинацией
     */
    suspend fun listWorkouts(page: Int, pageSize: Int): List<Workout>

    /**
     * Получить список упражнений в тренировке по Id
     */
    suspend fun getWorkoutExercises(workoutId: String): List<Exercise>

    /**
     * Создать тренировку и получить id
     */
    suspend fun createWorkout(workout: Workout): Workout

    /**
     * Обновить тренировку по id
     */
    suspend fun updateWorkout(id: String, workout: Workout): Workout

    /**
     * Удалить тренировку
     */
    suspend fun deleteWorkout(id: String)

    /**
     * Создать кастомную тренировку
     */
    suspend fun createCustomWorkout(workout: Workout): String
}
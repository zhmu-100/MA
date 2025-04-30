package com.zhmu100.ma.domain.api.training

import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseReaction
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import java.util.UUID

class TrainingApiMock : TrainingApi {
    // In-memory хранилище тренировок
    private val workouts = mutableListOf<Workout>()
    private val exercises = mutableMapOf<String, List<Exercise>>()

    init {
        // Инициализация тестовыми данными
        val testWorkout = Workout(
            id = "workout_1",
            name = "Пробежка",
            date = "2023-05-15T08:00:00Z",
            exercises = listOf(
                Exercise(
                    name = ExerciseName.RUNNING,
                    duration = "PT30M",
                    exerciseType = ExerciseType.DYNAMIC,
                    distance = 3000,
                    reaction = ExerciseReaction.EXCELLENT,
                    note = "Как я рад, что смог пробежать"
                )
            )
        )
        workouts.add(testWorkout)
        exercises["workout_1"] = testWorkout.exercises
    }

    override suspend fun getWorkout(id: String): Workout {
        return workouts.find { it.id == id }
            ?: throw IllegalArgumentException("Workout not found")
    }

    override suspend fun listWorkouts(page: Int, pageSize: Int): List<Workout> {
        val from = (page - 1) * pageSize
        val to = minOf(from + pageSize, workouts.size)
        return workouts.subList(from, to)
    }

    override suspend fun getWorkoutExercises(workoutId: String): List<Exercise> {
        return exercises[workoutId]
            ?: throw IllegalArgumentException("Workout exercises not found")
    }

    override suspend fun createWorkout(workout: Workout): Workout {
        val id = "workout_${UUID.randomUUID()}"
        val newWorkout = workout.copy(id = id)
        workouts.add(newWorkout)
        exercises[id] = workout.exercises
        return newWorkout
    }

    override suspend fun updateWorkout(id: String, workout: Workout): Workout {
        val index = workouts.indexOfFirst { it.id == id }
        if (index == -1) throw IllegalArgumentException("Workout not found")

        val updatedWorkout = workout.copy(id = id)
        workouts[index] = updatedWorkout
        exercises[id] = workout.exercises
        return updatedWorkout
    }

    override suspend fun deleteWorkout(id: String) {
        workouts.removeIf { it.id == id }
        exercises.remove(id)
    }

    override suspend fun createCustomWorkout(workout: Workout): String {
        val id = "custom_${UUID.randomUUID()}"
        val newWorkout = workout.copy(id = id)
        workouts.add(newWorkout)
        exercises[id] = workout.exercises
        return id
    }

    // Методы для управления mock-данными (для тестов)
    fun clearData() {
        workouts.clear()
        exercises.clear()
    }

    fun getStoredWorkouts(): List<Workout> = workouts.toList()

    fun addTestWorkout(workout: Workout) {
        workouts.add(workout)
        workout.id?.let { exercises[it] = workout.exercises }
    }
}
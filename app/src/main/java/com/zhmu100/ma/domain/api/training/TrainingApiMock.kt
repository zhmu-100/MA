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
        // Инициализация тестовыми данными с датами в диапазоне 20.04.2025 - 05.05.2025
        val testWorkouts = listOf(
            Workout(
                id = "workout_1",
                name = "Пробежка",
                date = "2025-04-20T08:00:00Z",
                exercises = listOf(
                    Exercise(
                        id = "exercise_0",
                        name = ExerciseName.RUNNING,
                        duration = "PT30M",
                        exerciseType = ExerciseType.DYNAMIC,
                        distance = 3000,
                        steps = 2000,
                        reaction = ExerciseReaction.EXCELLENT,
                        note = "Как я рад, что смог пробежать"
                    )
                )
            ),
            Workout(
                id = "workout_2",
                name = "Силовая тренировка",
                date = "2025-04-22T09:00:00Z",
                exercises = listOf(
                    Exercise(
                        id = "exercise_1",
                        name = ExerciseName.PUSHUPS,
                        duration = "PT15M",
                        exerciseType = ExerciseType.STATIC,
                        sets = 3,
                        reps = 15,
                        reaction = ExerciseReaction.GOOD,
                        note = "Чувствую себя сильнее!"
                    ),
                    Exercise(
                        id = "exercise_2",
                        name = ExerciseName.SQUATS,
                        duration = "PT20M",
                        exerciseType = ExerciseType.STATIC,
                        sets = 4,
                        reps = 12,
                        reaction = ExerciseReaction.OK,
                        note = "Немного устал, но это нормально."
                    )
                )
            ),
            Workout(
                id = "workout_3",
                name = "Велопрогулка",
                date = "2025-04-24T07:30:00Z",
                exercises = listOf(
                    Exercise(
                        id = "exercise_3",
                        name = ExerciseName.CYCLING,
                        duration = "PT45M",
                        exerciseType = ExerciseType.DYNAMIC,
                        distance = 15000,
                        steps = 8000,
                        reaction = ExerciseReaction.EXCELLENT,
                        note = "Прекрасная погода для велопрогулки!"
                    )
                )
            ),
            Workout(
                id = "workout_4",
                name = "Планка",
                date = "2025-04-26T06:00:00Z",
                exercises = listOf(
                    Exercise(
                        id = "exercise_4",
                        name = ExerciseName.PLANK,
                        duration = "PT5M",
                        exerciseType = ExerciseType.STATIC,
                        reaction = ExerciseReaction.GOOD,
                        note = "Удерживал планку 5 минут."
                    )
                )
            ),
            Workout(
                id = "workout_5",
                name = "Тренировка с отжиманиями и подтягиваниями",
                date = "2025-05-01T10:00:00Z",
                exercises = listOf(
                    Exercise(
                        id = "exercise_5",
                        name = ExerciseName.PULLUPS,
                        duration = "PT10M",
                        exerciseType = ExerciseType.STATIC,
                        sets = 3,
                        reps = 8,
                        reaction = ExerciseReaction.OK,
                        note = "Подтягивания идут тяжело, но я стараюсь!"
                    ),
                    Exercise(
                        id = "exercise_6",
                        name = ExerciseName.PUSHUPS,
                        duration = "PT10M",
                        exerciseType = ExerciseType.STATIC,
                        sets = 3,
                        reps = 10,
                        reaction = ExerciseReaction.GOOD,
                        note = "Отжимания были легче, чем ожидал."
                    )
                )
            )
        )

        workouts.addAll(testWorkouts)
        testWorkouts.forEach { workout ->
            if (workout.id != null) {
                exercises[workout.id] = workout.exercises
            }
        }
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
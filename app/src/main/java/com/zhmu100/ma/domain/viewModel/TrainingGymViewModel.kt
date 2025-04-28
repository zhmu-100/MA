package com.zhmu100.ma.domain.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import com.zhmu100.ma.ui.data.TrainRowData
import java.time.Duration
import java.time.Instant

class TrainingGymViewModel : ViewModel() {
    private val _trainRows = mutableStateListOf<TrainRowData>()
    val trainRows: SnapshotStateList<TrainRowData> = _trainRows

    private val _exerciseOptions = ExerciseName.values().filter {
        it != ExerciseName.UNSPECIFIED && it != ExerciseName.RUNNING && it != ExerciseName.CYCLING
    }
    val exerciseOptions: List<ExerciseName> = _exerciseOptions

    fun addExerciseRow() {
        _trainRows.add(TrainRowData(ExerciseName.UNSPECIFIED, ""))
    }

    fun updateExercise(index: Int, exercise: ExerciseName) {
        _trainRows[index] = _trainRows[index].copy(exercise = exercise)
    }

    fun updateValue(index: Int, value: String) {
        _trainRows[index] = _trainRows[index].copy(value = value)
    }

    fun removeExercise(index: Int) {
        _trainRows.removeAt(index)
    }

    fun getWorkout(
        totalExerciseTime: Duration,
        workoutName: String = "Тренировка в зале",
        customDate: String? = null
    ): Workout? {
        if (trainRows.size == 0)
            return null

        val exercises = trainRows.map { row ->
            Exercise(
                name = row.exercise,
                exerciseType = ExerciseType.STATIC,
                reps = row.value.toIntOrNull() ?: 0,
                duration = totalExerciseTime.toString()
            )
        }
        return Workout(
            name = workoutName,
            date = customDate ?: Instant.now().toString(),
            exercises = exercises
        )
    }
}
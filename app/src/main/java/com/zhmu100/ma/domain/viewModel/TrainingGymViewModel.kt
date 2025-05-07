package com.zhmu100.ma.domain.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import com.zhmu100.ma.domain.storage.DeviceStorage
import com.zhmu100.ma.ui.data.TrainRowData
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

class TrainingGymViewModel(
    private val deviceStorage: DeviceStorage
) : ViewModel() {
    private var _trainRows = mutableStateListOf<TrainRowData>()
    val trainRows: SnapshotStateList<TrainRowData> = _trainRows

    private val _exerciseOptions = ExerciseName.entries.filter {
        it != ExerciseName.EXERCISE_NAME_UNSPECIFIED && it != ExerciseName.EXERCISE_NAME_RUNNING && it != ExerciseName.EXERCISE_NAME_CYCLING
    }
    val exerciseOptions: List<ExerciseName> = _exerciseOptions

    fun addExerciseRow() {
        _trainRows.add(TrainRowData(ExerciseName.EXERCISE_NAME_UNSPECIFIED, ""))
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

    fun clearStats() {
        _trainRows.clear()
    }

    fun getWorkout(
        totalExerciseTime: Duration,
        workoutName: String = "Тренировка в зале",
        customDate: String? = null
    ): Workout? {
        if (trainRows.size == 0)
            return null

        val devices = deviceStorage.getDevicesByType(DeviceType.WATCH)
        val device = devices.firstOrNull()

        val exercises = trainRows.map { row ->
            Exercise(
                name = row.exercise,
                excercise_type = ExerciseType.EXERCISE_TYPE_STATIC,
                reps = row.value.toIntOrNull() ?: 0,
                duration = LocalDateTime.now().toString(),
                bmp = device?.getReading()?.value?.toInt()
            )
        }
        return Workout(
            name = workoutName,
            date = customDate ?: Instant.now().toString(),
            excercises = exercises
        )
    }
}
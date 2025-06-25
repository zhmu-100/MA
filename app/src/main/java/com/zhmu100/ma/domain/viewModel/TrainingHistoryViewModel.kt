package com.zhmu100.ma.domain.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.zhmu100.ma.domain.api.statistic.StatisticsApi
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.model.statistic.GPSPosition
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import com.zhmu100.ma.domain.model.training.WorkoutStats
import com.zhmu100.ma.domain.utils.DurationUtils
import com.zhmu100.ma.domain.utils.GPSStats.calculateCalories
import com.zhmu100.ma.domain.utils.GPSStats.calculateSteps
import com.zhmu100.ma.domain.utils.GPSStats.calculateTotalDistance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration

class TrainingHistoryViewModel(
    private val trainingApi: TrainingApi,
    private val statisticsApi: StatisticsApi
) : ViewModel() {
    private val _workoutsState = MutableStateFlow<ViewState<List<Workout>>>(ViewState.Uninitialized)
    val workoutsState = _workoutsState.asStateFlow()

    private val _filteredWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val filteredWorkouts = _filteredWorkouts.asStateFlow()

    private val _currentGPSData = MutableStateFlow<List<GPSPosition>>(emptyList())
    val currentGPSData = _currentGPSData.asStateFlow()

    private val _currentWorkoutStats = MutableStateFlow<WorkoutStats?>(null)
    val currentWorkoutStats = _currentWorkoutStats.asStateFlow()

    private var currentExerciseType: ExerciseType? = null

    fun loadWorkouts(forceRefresh: Boolean = false) {
        if (_workoutsState.value is ViewState.Loading && !forceRefresh) {
            return
        }

        _workoutsState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                trainingApi.listWorkouts(page = 1, pageSize = 5)
            }.onSuccess { workouts ->
                _workoutsState.value = ViewState.Success(workouts.map{trainingApi.getWorkout(it.id)})
                currentExerciseType?.let { type ->
                    filterWorkoutsByExerciseType(type)
                } ?: run {
                    _filteredWorkouts.value = workouts.map{trainingApi.getWorkout(it.id)}
                }
            }.onFailure {
                _workoutsState.value = ViewState.Error("Error loading workouts: ${it.message}", it)
            }
        }
    }

    fun filterWorkoutsByExerciseType(exerciseType: ExerciseType) {
        currentExerciseType = exerciseType

        val workouts = when (_workoutsState.value) {
            is ViewState.Success -> (_workoutsState.value as ViewState.Success<List<Workout>>).data
            else -> return
        }

        _filteredWorkouts.value = workouts
            .filter { workout ->
                workout.excercises.any { it.excercise_type == exerciseType }
            }
    }

    fun loadGPSDataForWorkout(exerciseId: String, exercise: Exercise) {
        viewModelScope.launch {
            runCatching {
                statisticsApi.getGPSData(exerciseId)
            }.onSuccess { gpsDataList ->
                val positions = gpsDataList.flatMap { it.positions }
                Log.i("HTTP", positions.toString())
                _currentGPSData.value = positions
                calculateAndEmitStats(positions, exercise)
            }.onFailure {
                // Handle error (e.g., show empty state)
                Log.i("HTTP", it.toString())
                _currentGPSData.value = emptyList()
                _currentWorkoutStats.value = null
            }
        }
    }

    private fun calculateAndEmitStats(positions: List<GPSPosition>, exercise: Exercise) {
        if (positions.isEmpty()) {
            _currentWorkoutStats.value = null
            return
        }

        val latLngPoints = positions.map { LatLng(it.latitude, it.longitude) }
        val totalDistance = calculateTotalDistance(latLngPoints)
        val duration = DurationUtils.isoStringToDuration(exercise.duration) ?: Duration.ZERO
        val steps = calculateSteps(totalDistance)
        val calories =
            if (duration != Duration.ZERO) calculateCalories(totalDistance, duration) else 0

        _currentWorkoutStats.value = WorkoutStats(
            totalDistance = totalDistance,
            duration = duration,
            steps = steps,
            calories = calories
        )
    }

    fun clearError() {
        if (_workoutsState.value is ViewState.Error) {
            _workoutsState.value = ViewState.Uninitialized
        }
    }
}

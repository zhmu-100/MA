package com.zhmu100.ma.domain.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.model.training.ExerciseReaction
import com.zhmu100.ma.domain.model.training.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant

class TrainingViewModel(
    private val trainingApi: TrainingApi
) : ViewModel() {

    private var _isPaused = mutableStateOf(false)
    val isPaused: State<Boolean> = _isPaused

    private var _startTime = mutableStateOf<Instant?>(null)
    val startTime: State<Instant?> = _startTime

    private var _pausedTime = mutableStateOf<Instant?>(null)
    val pausedTime: State<Instant?> = _pausedTime

    private var _totalPausedDuration = mutableStateOf(Duration.ZERO)
    val totalPausedDuration: State<Duration> = _totalPausedDuration

    private var _totalExerciseTime = mutableStateOf(Duration.ZERO)
    val totalExerciseTime: State<Duration> = _totalExerciseTime

    private var _isTrainingStarted = mutableStateOf(false)
    val isTrainingStarted: State<Boolean> = _isTrainingStarted

    private var _currentWorkout = mutableStateOf<Workout?>(null)
    val currentWorkout: State<Workout?> = _currentWorkout

    private val _workoutState = MutableStateFlow<ViewState<Workout>>(ViewState.Uninitialized)
    val workoutState = _workoutState.asStateFlow()

    // Функции для управления тренировкой
    fun startTraining() {
        _isTrainingStarted.value = true
        _startTime.value = Instant.now()
        _currentWorkout.value = null
    }

    fun updateTimer() {
        val now = Instant.now()
        val pausedDuration = if (pausedTime.value != null) {
            Duration.between(pausedTime.value, now)
        } else {
            Duration.ZERO
        }
        if (startTime.value != null) {
            _totalExerciseTime.value = Duration.between(startTime.value, now)
                .minus(_totalPausedDuration.value)
                .plus(pausedDuration)
        }
    }

    fun togglePause() {
        if (_isPaused.value) {
            _totalPausedDuration.value = _totalPausedDuration.value.plus(
                Duration.between(_pausedTime.value, Instant.now())
            )
            _pausedTime.value = null
        } else {
            _pausedTime.value = Instant.now()
        }
        _isPaused.value = !_isPaused.value
    }

    fun rateTraining(workout: Workout) {
        _currentWorkout.value = workout
        if (!isPaused.value) {
            togglePause()
        }
    }

    fun clearTraining() {
        _isTrainingStarted.value = false
    }

    fun saveWorkout(workout: Workout, reaction: ExerciseReaction, note: String) {
        viewModelScope.launch {
            val finalExercises = workout.exercises.map {
                it.copy(note = note, reaction = reaction)
            }.toList()
            val finalWorkout = workout.copy(exercises = finalExercises)

            runCatching {
                trainingApi.createWorkout(finalWorkout)
            }.onSuccess { createdWorkout ->
                Log.i("TRAIN", createdWorkout.toString())
                _workoutState.value = ViewState.Success(
                    createdWorkout,
                    "Workout published"
                )
            }.onFailure { error ->
                _workoutState.value = ViewState.Error(
                    "Error publishing workout: ${error.message}",
                    error
                )
            }
            _isTrainingStarted.value = false
            _currentWorkout.value = null
        }
    }
}
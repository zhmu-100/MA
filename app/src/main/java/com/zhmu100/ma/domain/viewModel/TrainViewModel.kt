package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.model.training.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val trainingApi: TrainingApi
) : ViewModel() {
    private val _workoutState = MutableStateFlow<ViewState<Workout>>(ViewState.Uninitialized)
    val workoutState = _workoutState.asStateFlow()

    /**
     * Публикует новую тренировку
     * @param workout Полностью заполненная тренировка для отправки
     */
    fun publishWorkout(workout: Workout) {
        if (_workoutState.value is ViewState.Loading) {
            return
        }

        _workoutState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                trainingApi.createWorkout(workout)
            }.onSuccess { createdWorkout ->
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
        }
    }

    fun clearError() {
        if (_workoutState.value is ViewState.Error) {
            _workoutState.value = ViewState.Uninitialized
        }
    }
}
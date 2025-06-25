package com.zhmu100.ma.domain.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.zhmu100.ma.domain.model.device.DeviceType
import com.zhmu100.ma.domain.model.statistic.GPSPosition
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import com.zhmu100.ma.domain.storage.DeviceStorage
import com.zhmu100.ma.domain.utils.GPSStats.calculateCalories
import com.zhmu100.ma.domain.utils.GPSStats.calculateSpeed
import com.zhmu100.ma.domain.utils.GPSStats.calculateSteps
import com.zhmu100.ma.domain.utils.GPSStats.calculateTotalDistance
import com.zhmu100.ma.domain.utils.GPSStats.distance
import java.time.Duration
import java.time.LocalDateTime

const val DEFAULT_DISTANCE_THRESHOLD = 20.0 // meters

class TrainingMapViewModel(
    private val deviceStorage: DeviceStorage
) : ViewModel() {
    private var _routePoints = mutableStateListOf<GPSPosition>()
    val routePoints: SnapshotStateList<GPSPosition> = _routePoints
    private var _totalDistance = mutableDoubleStateOf(0.0)
    val totalDistance: State<Double> = _totalDistance
    private var _stepsCount = mutableIntStateOf(0)
    val stepsCount: State<Int> = _stepsCount
    private var _caloriesBurned = mutableIntStateOf(0)
    val caloriesBurned: State<Int> = _caloriesBurned

    fun clearStats() {
        _routePoints.clear()
        _totalDistance.doubleValue = 0.0
        _stepsCount.intValue = 0
        _caloriesBurned.intValue = 0
    }

    // Функции для управления тренировкой
    fun addNewPoint(newPoint: LatLng, totalExerciseTime: Duration) {
        val newGpsData = GPSPosition(
            timestamp = LocalDateTime.now().toString(),
            latitude = newPoint.latitude,
            longitude = newPoint.longitude,
            speed = calculateSpeed(totalExerciseTime, totalDistance.value),
            accuracy = 100.0,
            altitude = 0.0
        )

        if (_routePoints.isEmpty()) {
            _routePoints.add(newGpsData)
            return
        }

        val lastPoint = _routePoints.last()
        val distance = distance(lastPoint.toLatLng(), newPoint)
        if (distance >= DEFAULT_DISTANCE_THRESHOLD) {
            _routePoints.add(newGpsData)
            updateStats(totalExerciseTime)
        }
    }

    fun getWorkout(
        totalExerciseTime: Duration,
        workoutName: String = "Беговая тренировка",
        customDate: String? = null
    ): Workout? {
        if (routePoints.isEmpty()) return null

        val devices = deviceStorage.getDevicesByType(DeviceType.WATCH)
        val device = devices.firstOrNull()
        val speed = calculateSpeed(totalExerciseTime, totalDistance.value)

        val exercise = Exercise(
            name = ExerciseName.EXERCISE_NAME_RUNNING,
            excercise_type = ExerciseType.EXERCISE_TYPE_DYNAMIC,
            duration = LocalDateTime.now().toString(),
            distance = totalDistance.value.toInt(),
            steps = stepsCount.value,
            calories = caloriesBurned.value.toDouble(),
            speed = speed.toInt(),
            bmp = device?.getReading()?.value?.toInt()
        )
        return Workout(
            name = workoutName,
            date = customDate ?: java.time.Instant.now().toString(),
            excercises = listOf(exercise)
        )
    }

    private fun updateStats(totalExerciseTime: Duration) {
        _totalDistance.doubleValue = calculateTotalDistance(routePoints.map { it.toLatLng() })
        _stepsCount.intValue = calculateSteps(totalDistance.value)
        _caloriesBurned.intValue = calculateCalories(totalDistance.value, totalExerciseTime)
    }
}
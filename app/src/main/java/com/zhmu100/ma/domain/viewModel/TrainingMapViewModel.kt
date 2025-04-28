package com.zhmu100.ma.domain.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.utils.sphericalDistance
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.Workout
import java.time.Duration

const val DEFAULT_DISTANCE_THRESHOLD = 20.0 // meters
const val AVERAGE_STEP_LENGTH = 0.762 // meters
const val CALORIES_PER_KM = 60
const val CALORIES_SPEED_FACTOR = 1 / 12

class TrainingMapViewModel(
    private val trainingApi: TrainingApi
) : ViewModel() {
    private var _routePoints = mutableStateListOf<LatLng>()
    val routePoints: SnapshotStateList<LatLng> = _routePoints
    private var _totalDistance = mutableDoubleStateOf(0.0)
    val totalDistance: State<Double> = _totalDistance
    private var _stepsCount = mutableIntStateOf(0)
    val stepsCount: State<Int> = _stepsCount
    private var _caloriesBurned = mutableIntStateOf(0)
    val caloriesBurned: State<Int> = _caloriesBurned

    fun clearStats() {
        _routePoints = mutableStateListOf<LatLng>()
        _totalDistance = mutableDoubleStateOf(0.0)
        _stepsCount = mutableIntStateOf(0)
        _caloriesBurned = mutableIntStateOf(0)
    }

    // Функции для управления тренировкой
    fun addNewPoint(newPoint: LatLng, totalExerciseTime: Duration) {
        if (_routePoints.isEmpty()) {
            _routePoints.add(newPoint)
            return
        }

        val lastPoint = _routePoints.last()
        val distance = distance(lastPoint, newPoint)
        if (distance >= DEFAULT_DISTANCE_THRESHOLD) {
            _routePoints.add(newPoint)
            updateStats(totalExerciseTime)
        }
    }

    fun getWorkout(
        totalExerciseTime: Duration,
        workoutName: String = "Беговая тренировка",
        customDate: String? = null
    ): Workout? {
        if (routePoints.isEmpty()) return null

        val hours = totalExerciseTime.toHours().toDouble() +
                totalExerciseTime.toMinutes().toDouble() / 60 +
                totalExerciseTime.seconds.toDouble() / 3600

        val speed = if (hours > 0) {
            ((totalDistance.value / 1000) / hours).toInt()
        } else {
            0
        }

        val exercise = Exercise(
            name = ExerciseName.RUNNING,
            exerciseType = ExerciseType.DYNAMIC,
            duration = totalExerciseTime.toString(),
            distance = totalDistance.value.toInt(),
            steps = stepsCount.value,
            calories = caloriesBurned.value.toDouble(),
            speed = speed
        )
        return Workout(
            name = workoutName,
            date = customDate ?: java.time.Instant.now().toString(),
            exercises = listOf(exercise)
        )
    }

    private fun updateStats(totalExerciseTime: Duration) {
        _totalDistance.doubleValue = calculateTotalDistance(routePoints)
        _stepsCount.intValue = calculateSteps(totalDistance.value)
        _caloriesBurned.intValue = calculateCalories(totalDistance.value, totalExerciseTime)
    }

    /**
     * Calculates distance between two points in meters
     */
    private fun distance(point1: LatLng, point2: LatLng): Double {
        return point1.sphericalDistance(point2)
    }

    /**
     * Calculates total distance of the route in meters
     */
    private fun calculateTotalDistance(points: List<LatLng>): Double {
        if (points.size < 2) return 0.0
        return points.zipWithNext { a, b -> distance(a, b) }.sum()
    }

    /**
     * Calculates steps count based on distance (approximate)
     */
    private fun calculateSteps(distance: Double): Int {
        return (distance / AVERAGE_STEP_LENGTH).toInt()
    }

    /**
     * Calculates burned calories based on distance and time
     */
    private fun calculateCalories(distance: Double, duration: Duration): Int {
        val km = distance / 1000
        val hours = duration.toHours().toDouble() + duration.toMinutes().toDouble() / 60
        val speed = if (hours > 0) km / hours else 0.0
        return (km * CALORIES_PER_KM * (1 + speed * CALORIES_SPEED_FACTOR)).toInt()
    }
}
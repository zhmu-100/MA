package com.zhmu100.ma.domain.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.utils.sphericalDistance
import java.time.Duration

object GPSStats {
    private const val AVERAGE_STEP_LENGTH = 0.762 // meters
    private const val CALORIES_PER_KM = 60
    private const val CALORIES_SPEED_FACTOR = 1 / 12

    /**
     * Calculates distance between two points in meters
     */
    fun distance(point1: LatLng, point2: LatLng): Double {
        return point1.sphericalDistance(point2)
    }

    /**
     * Calculates total distance of the route in meters
     */
    fun calculateTotalDistance(points: List<LatLng>): Double {
        if (points.size < 2) return 0.0
        return points.zipWithNext { a, b -> distance(a, b) }.sum()
    }

    /**
     * Calculates steps count based on distance (approximate)
     */
    fun calculateSteps(distance: Double): Int {
        return (distance / AVERAGE_STEP_LENGTH).toInt()
    }

    /**
     * Calculates burned calories based on distance and time
     */
    fun calculateCalories(distance: Double, duration: Duration): Int {
        val km = distance / 1000
        val speed = calculateSpeed(duration, distance)
        return (km * CALORIES_PER_KM * (1 + speed * CALORIES_SPEED_FACTOR)).toInt()
    }

    /**
     * Calculates current speed
     */
    fun calculateSpeed(duration: Duration, distance: Double): Double {
        val hours = duration.toHours().toDouble() +
                duration.toMinutes().toDouble() / 60 +
                duration.seconds.toDouble() / 3600

        val speed = if (hours > 0) {
            ((distance / 1000) / hours)
        } else {
            0.0
        }
        return speed
    }
}
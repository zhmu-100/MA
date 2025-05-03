package com.zhmu100.ma.domain.api.statistic

import com.zhmu100.ma.domain.model.statistic.CaloriesData
import com.zhmu100.ma.domain.model.statistic.ExerciseMetadata
import com.zhmu100.ma.domain.model.statistic.GPSData
import com.zhmu100.ma.domain.model.statistic.GPSPosition
import com.zhmu100.ma.domain.model.statistic.HeartRateData
import com.zhmu100.ma.domain.model.statistic.UserMetadata
import java.time.Instant
import kotlin.random.Random

class StatisticsApiMock : StatisticsApi {
    private val fakeGPSData = mutableListOf<GPSData>()
    private val fakeHeartRateData = mutableListOf<HeartRateData>()
    private val fakeCaloriesData = mutableListOf<CaloriesData>()

    init {
        // Инициализация фейковыми данными
        repeat(10) { exerciseNum ->
            val exerciseId = "exercise_$exerciseNum"
            fakeGPSData.add(createFakeGPSData(exerciseId))
            fakeHeartRateData.add(createFakeHeartRateData(exerciseId))
        }

        repeat(3) { userId ->
            fakeCaloriesData.add(createFakeCaloriesData("user_$userId"))
        }
    }

    override suspend fun getGPSData(exerciseId: String): List<GPSData> {
        return fakeGPSData.filter { it.meta.exerciseId == exerciseId }
    }

    override suspend fun uploadGPSData(gpsData: GPSData) {
        fakeGPSData.add(gpsData)
    }

    override suspend fun getHeartRateData(exerciseId: String): List<HeartRateData> {
        return fakeHeartRateData.filter { it.meta.exerciseId == exerciseId }
    }

    override suspend fun uploadHeartRateData(heartRateData: HeartRateData) {
        fakeHeartRateData.add(heartRateData)
    }

    override suspend fun getCaloriesData(userId: String): List<CaloriesData> {
        return fakeCaloriesData.filter { it.meta.userId == userId }
    }

    override suspend fun uploadCaloriesData(caloriesData: CaloriesData) {
        fakeCaloriesData.add(caloriesData)
    }

    private fun createFakeGPSData(exerciseId: String): GPSData {
        val positions = List(10) { index ->
            GPSPosition(
                timestamp = Instant.now().minusSeconds(index * 10L).toString(),
                latitude = 55.7558 + Random.nextDouble(-0.01, 0.01),
                longitude = 37.6173 + Random.nextDouble(-0.01, 0.01),
                altitude = Random.nextDouble(100.0, 200.0),
                speed = Random.nextDouble(0.0, 10.0),
                accuracy = Random.nextDouble(1.0, 5.0)
            )
        }

        return GPSData(
            meta = ExerciseMetadata(
                exerciseId = exerciseId,
                timestamp = Instant.now().toString()
            ),
            positions = positions
        )
    }

    private fun createFakeHeartRateData(exerciseId: String): HeartRateData {
        return HeartRateData(
            meta = ExerciseMetadata(
                exerciseId = exerciseId,
                timestamp = Instant.now().toString()
            ),
            bpm = Random.nextInt(60, 180)
        )
    }

    private fun createFakeCaloriesData(userId: String): CaloriesData {
        return CaloriesData(
            meta = UserMetadata(
                userId = userId,
                timestamp = Instant.now().toString()
            ),
            calories = Random.nextDouble(100.0, 1000.0)
        )
    }
}
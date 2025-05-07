package com.zhmu100.ma.domain.api.statistic

import com.zhmu100.ma.domain.model.statistic.CaloriesData
import com.zhmu100.ma.domain.model.statistic.GPSData
import com.zhmu100.ma.domain.model.statistic.GPSDataResponse
import com.zhmu100.ma.domain.model.statistic.HeartRateData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class StatisticsApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : StatisticsApi {

    override suspend fun getGPSData(exerciseId: String): List<GPSData> {
        return client.get("$baseUrl/gps") {
            parameter("exercise_id", exerciseId)
        }.body<GPSDataResponse>().gps_data
    }

    override suspend fun uploadGPSData(gpsData: GPSData) {
        client.post("$baseUrl/gps") {
            contentType(ContentType.Application.Json)
            setBody(gpsData)
        }
    }

    override suspend fun getHeartRateData(exerciseId: String): List<HeartRateData> {
        return client.get("$baseUrl/heartrate") {
            parameter("exerciseId", exerciseId)
        }.body()
    }

    override suspend fun uploadHeartRateData(heartRateData: HeartRateData) {
        client.post("$baseUrl/heartrate") {
            contentType(ContentType.Application.Json)
            setBody(heartRateData)
        }
    }

    override suspend fun getCaloriesData(userId: String): List<CaloriesData> {
        return client.get("$baseUrl/calories") {
            parameter("userId", userId)
        }.body()
    }

    override suspend fun uploadCaloriesData(caloriesData: CaloriesData) {
        client.post("$baseUrl/calories") {
            contentType(ContentType.Application.Json)
            setBody(caloriesData)
        }
    }
}
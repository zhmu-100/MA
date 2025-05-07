package com.zhmu100.ma.domain.api.statistic

import com.zhmu100.ma.domain.model.statistic.CaloriesData
import com.zhmu100.ma.domain.model.statistic.GPSData
import com.zhmu100.ma.domain.model.statistic.GPSDataResponse
import com.zhmu100.ma.domain.model.statistic.HeartRateData

/**
 * API для общения с сервисом статистики
 * Обслуживает GPS данные тренировок, сердечные ритмы тренировок, калирии по еде
 */
interface StatisticsApi {
    /**
     * Получить список GPS меток для определенного упражнения по его id
     */
    suspend fun getGPSData(exerciseId: String): List<GPSData>

    /**
     * Отравить список GPS меток для определенного упражнения
     */
    suspend fun uploadGPSData(gpsData: GPSData)

    /**
     * Получить список сердечных ритмов для определенного упражнения по его id
     */
    suspend fun getHeartRateData(exerciseId: String): List<HeartRateData>
    /**
     * Отравить список сердечных ритмов меток для определенного упражнения
     */
    suspend fun uploadHeartRateData(heartRateData: HeartRateData)

    /**
     * Получить список съеденных калорий пользователя по его id
     */
    suspend fun getCaloriesData(userId: String): List<CaloriesData>
    /**
     * Отравить съеденные калории пользователя
     */
    suspend fun uploadCaloriesData(caloriesData: CaloriesData)
}
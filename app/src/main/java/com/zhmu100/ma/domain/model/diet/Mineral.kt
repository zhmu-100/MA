package com.zhmu100.ma.domain.model.diet

import kotlinx.serialization.Serializable

/**
 * Модель, представляющая минерал в составе пищи.
 *
 * @property id Уникальный идентификатор минерала (может быть null для новых записей)
 * @property name Название минерала (например, "Кальций", "Железо")
 * @property amount Количество минерала
 * @property unit Единица измерения количества (например, "mg", "mcg")
 *
 * Пример:
 * ```
 * Mineral(
 *     id = "iron_456",
 *     name = "Iron",
 *     amount = 18.0,
 *     unit = "mg"
 * )
 * ```
 */
@Serializable
data class Mineral(
    val id: String? = null,
    val name: String,
    val amount: Double,
    val unit: String
) {
    fun withPortion(factor: Double): Mineral {
        return copy(amount = amount * factor)
    }
}
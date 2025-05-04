package com.zhmu100.ma.domain.model.diet

/**
 * Модель, представляющая витамин в составе пищи.
 *
 * @property id Уникальный идентификатор витамина (может быть null для новых записей)
 * @property name Название витамина (например, "Витамин C", "Витамин D3")
 * @property amount Количество витамина
 * @property unit Единица измерения количества (например, "mg", "mcg", "IU")
 *
 * Пример:
 * ```
 * Vitamin(
 *     id = "vit_c_123",
 *     name = "Vitamin C",
 *     amount = 90.0,
 *     unit = "mg"
 * )
 * ```
 */
data class Vitamin(
    val id: String? = null,
    val name: String,
    val amount: Double,
    val unit: String
) {
    fun withPortion(factor: Double): Vitamin {
        return copy(amount = amount * factor)
    }
}
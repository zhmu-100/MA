package com.zhmu100.ma.ui.data

import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Модель данных для заметки
 *
 * @property id Уникальный идентификатор заметки
 * @property title Заголовок заметки
 * @property content Содержимое заметки
 * @property createdAt Время создания заметки в миллисекундах
 * @property updatedAt Время последнего обновления заметки в миллисекундах
 */
@Serializable
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Новая заметка",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

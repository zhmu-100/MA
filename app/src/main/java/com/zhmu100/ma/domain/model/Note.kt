package com.zhmu100.ma.domain.model


import kotlinx.serialization.Serializable

/**
 * Класс данных содержащий информацию о заметке
 * @property id
 * @property userId
 * @property title
 * @property content
 */
@Serializable
data class Note(
    val id: String? =null,
    val userId: String? =null,
    val title: String? =null,
    val content: String? =null
)

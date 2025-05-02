package com.zhmu100.ma.ui.data

/**
 * Модель данных, представляющая одну рекомендацию по питанию.
 *
 * @property id Уникальный идентификатор рекомендации.
 * @property title Заголовок или название рекомендации.
 * @property date Дата публикации в формате строки.
 * @property imageUrl URL изображения (не используется в текущей реализации, заменён локальным ресурсом).
 * @property url Ссылка на подробности рекомендации.
 */

data class Recommendation(
    val id: Int,
    val title: String,
    val date: String,
    val imageUrl: String,
    val url: String
)
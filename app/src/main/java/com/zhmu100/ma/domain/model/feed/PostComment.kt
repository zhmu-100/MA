package com.zhmu100.ma.domain.model.feed

import kotlinx.serialization.Serializable

@Serializable
data class PostComment(
    val id: String,
    val user_id: String,
    val content: String,
    val date: String,
    val reactions: List<PostReaction> = emptyList()
)
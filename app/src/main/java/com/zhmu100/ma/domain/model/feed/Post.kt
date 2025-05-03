package com.zhmu100.ma.domain.model.feed

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String,
    val user_id: String,
    val content: String? = null,
    val attachments: List<PostAttachment> = emptyList(),
    val date: String,
    val reactions: List<PostReaction> = emptyList(),
    val comments: List<PostComment> = emptyList()
)
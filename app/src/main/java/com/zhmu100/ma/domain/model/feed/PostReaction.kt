package com.zhmu100.ma.domain.model.feed

import kotlinx.serialization.Serializable

@Serializable
data class PostReaction(
    val post_id: String,
    val user_id: String,
    val reaction: Reaction
)
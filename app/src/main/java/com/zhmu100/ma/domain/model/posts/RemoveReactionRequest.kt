package com.zhmu100.ma.domain.model.posts

import kotlinx.serialization.Serializable

@Serializable
data class RemoveReactionRequest(
    val postId: String,
    val userId: String
)

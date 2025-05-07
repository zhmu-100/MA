package com.zhmu100.ma.domain.model.posts

import kotlinx.serialization.Serializable


@Serializable
data class PostsResponse(
    val posts: List<Post>
)

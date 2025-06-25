package com.zhmu100.ma.domain.model.posts

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String?=null,
    val userId: String,
    val content: String?=null,
    val date: String?=null,
    val attachments: List<Attachment> = emptyList()
)
package com.zhmu100.ma.domain.model.posts

import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    val id: String?=null,
    val postId: String?=null,
    val type: AttachmentType,
    val position: Int,
    val minioId: String
)
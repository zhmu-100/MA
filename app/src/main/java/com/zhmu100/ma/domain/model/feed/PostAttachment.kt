package com.zhmu100.ma.domain.model.feed

import kotlinx.serialization.Serializable

@Serializable
data class PostAttachment(
    val id: String,
    val post_id: String,
    val type: AttachmentType,
    val position: Int,
    val minio_id: String
)
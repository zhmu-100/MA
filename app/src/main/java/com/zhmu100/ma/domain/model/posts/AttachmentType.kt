package com.zhmu100.ma.domain.model.posts

import kotlinx.serialization.Serializable

@Serializable
enum class AttachmentType {
    ATTACHMENT_TYPE_UNSPECIFIED,
    ATTACHMENT_TYPE_IMAGE,
    ATTACHMENT_TYPE_VIDEO
}
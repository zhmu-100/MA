package com.zhmu100.ma.domain.model.files

import kotlinx.serialization.Serializable

@Serializable
data class FilesResponse(val id: String)

@Serializable
data class UrlResponse(val url: String)

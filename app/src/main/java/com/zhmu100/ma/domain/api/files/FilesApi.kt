package com.zhmu100.ma.domain.api.files

import kotlinx.coroutines.flow.Flow

interface FilesApi {
    suspend fun getFile(id: String): ByteArray
    suspend fun getFileAsFlow(id: String): Flow<ByteArray>
    suspend fun getFileUrl(id: String): String
    suspend fun uploadFile(file: ByteArray, fileName: String, mimeType: String): String
    suspend fun fixUpload(id: String, file: ByteArray, fileName: String, mimeType: String): String
}
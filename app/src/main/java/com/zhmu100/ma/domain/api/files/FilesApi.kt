package com.zhmu100.ma.domain.api.files

import com.zhmu100.ma.domain.model.files.FileMetadata
import com.zhmu100.ma.domain.model.files.FilesResponse
import com.zhmu100.ma.domain.model.files.UrlResponse
import kotlinx.coroutines.flow.Flow

/**
 * API для общения с сервисом файлового хранилища.
 * Можно загружать файлы, заменять файлы, получать файл или ссылку на него по id
 */
interface FilesApi {
    /**
     * Получить файл по id
     */
    suspend fun getFile(id: String): ByteArray
    /**
     * Получить файл в виде потока по id
     */
    suspend fun getFileAsFlow(id: String): Flow<ByteArray>
    /**
     * Получить ссылку на файл по id
     */
    suspend fun getFileUrl(id: String): UrlResponse
    /**
     * Отправить файл, получить id
     */
    suspend fun uploadFile(
        file: ByteArray,
        fileName: String,
        mimeType: String,
        metadata: FileMetadata,
        userId: String
    ): FilesResponse
    /**
     * Заменить файл по id
     */
    suspend fun fixUpload(id: String, file: ByteArray, fileName: String, mimeType: String): String
}
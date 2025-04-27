package com.zhmu100.ma.domain.api.files

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class FilesApiMock : FilesApi {
    // Хранилище файлов в памяти (id to Pair(data, metadata))
    private val filesStorage = mutableMapOf<String, Pair<ByteArray, FileMetadata>>()

    override suspend fun getFile(id: String): ByteArray {
        return filesStorage[id]?.first ?: throw IllegalArgumentException("File not found")
    }

    override suspend fun getFileAsFlow(id: String): Flow<ByteArray> = flow {
        val file = filesStorage[id]?.first ?: throw IllegalArgumentException("File not found")
        // Эмулируем потоковую передачу по частям
        val chunkSize = 1024
        var offset = 0
        while (offset < file.size) {
            val end = minOf(offset + chunkSize, file.size)
            emit(file.copyOfRange(offset, end))
            offset = end
        }
    }

    override suspend fun getFileUrl(id: String): String {
        if (!filesStorage.containsKey(id)) {
            throw IllegalArgumentException("File not found")
        }
//        return "https://mock.storage/files/$id"
        return "https://i.pinimg.com/originals/4d/e6/08/4de60873cd38fa9c3ba6facd4e896929.jpg" // cat
    }

    override suspend fun uploadFile(file: ByteArray, fileName: String, mimeType: String): String {
        val id = UUID.randomUUID().toString()
        filesStorage[id] = file to FileMetadata(fileName, mimeType)
        return id
    }

    override suspend fun fixUpload(id: String, file: ByteArray, fileName: String, mimeType: String): String {
        if (!filesStorage.containsKey(id)) {
            throw IllegalArgumentException("File not found")
        }
        filesStorage[id] = file to FileMetadata(fileName, mimeType)
        return id
    }

    // Вспомогательный класс для хранения метаданных файла
    private data class FileMetadata(
        val fileName: String,
        val mimeType: String
    )

    // Методы для управления mock-данными (для тестов)
    fun clearStorage() {
        filesStorage.clear()
    }

    fun getStoredFileIds(): List<String> {
        return filesStorage.keys.toList()
    }
}
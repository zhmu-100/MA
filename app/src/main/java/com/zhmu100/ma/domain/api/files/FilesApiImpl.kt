package com.zhmu100.ma.domain.api.files

import com.zhmu100.ma.domain.model.files.FileMetadata
import com.zhmu100.ma.domain.model.files.FilesResponse
import com.zhmu100.ma.domain.model.files.UrlResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.nio.ByteBuffer

class FilesApiImpl(private val client: HttpClient, private val baseUrl: String) : FilesApi {
    override suspend fun getFile(id: String): ByteArray {
        return client.get("$baseUrl/$id") {
            accept(ContentType.Application.OctetStream)
        }.body()
    }

    override suspend fun getFileAsFlow(id: String): Flow<ByteArray> = flow {
        val channel = client.get("$baseUrl/$id/stream") {
            accept(ContentType.Application.OctetStream)
        }.body<ByteReadChannel>()

        val buffer = ByteBuffer.allocate(8192)

        while (!channel.isClosedForRead) {
            buffer.clear()
            val bytesRead = channel.readAvailable(buffer)

            if (bytesRead > 0) {
                val resultArray = ByteArray(bytesRead)
                buffer.flip()
                buffer.get(resultArray)
                emit(resultArray)
            }
        }
    }

    override suspend fun getFileUrl(id: String): UrlResponse {
        return client.get("$baseUrl/url/$id").body()
    }

    override suspend fun uploadFile(
        file: ByteArray,
        fileName: String,
        mimeType: String,
        metadata: FileMetadata,
        userId: String
    ): FilesResponse {
        return client.post("$baseUrl/upload") {
            contentType(ContentType.MultiPart.FormData)
            header("X-User-Id", userId) // Добавляем user ID в заголовки
            setBody(
                MultiPartFormDataContent(
                    formData {
                        // Добавляем метаданные как JSON-строку
                        append(
                            "meta",
                            Json.encodeToString(metadata),
                            Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"meta\"")
                            }
                        )

                        // Добавляем файл
                        append(
                            "file",
                            file,
                            Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"file\"; filename=\"$fileName\""
                                )
                                append(HttpHeaders.ContentType, mimeType)
                            }
                        )
                    }
                )
            )
        }.body()
    }

    override suspend fun fixUpload(
        id: String,
        file: ByteArray,
        fileName: String,
        mimeType: String
    ): String {
        return client.post("$baseUrl/fix-upload/$id") {
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file",
                            file,
                            Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"file\"; filename=\"$fileName\""
                                )
                                append(HttpHeaders.ContentType, mimeType)
                            }
                        )
                    }
                )
            )
        }.body()
    }
}
package com.zhmu100.ma.domain.api.files

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.ByteBuffer

class FilesApiImpl(private val client: HttpClient, private val baseUrl: String) : FilesApi {
    override suspend fun getFile(id: String): ByteArray {
        return client.get("$baseUrl/files/$id") {
            accept(ContentType.Application.OctetStream)
        }.body()
    }

    override suspend fun getFileAsFlow(id: String): Flow<ByteArray> = flow {
        val channel = client.get("$baseUrl/files/$id/stream") {
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

    override suspend fun getFileUrl(id: String): String {
        return client.get("$baseUrl/files/$id/url").body()
    }

    override suspend fun uploadFile(file: ByteArray, fileName: String, mimeType: String): String {
        return client.post("$baseUrl/files/upload") {
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file",
                            file,
                            Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"; filename=\"$fileName\"")
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
        return client.post("$baseUrl/files/fix-upload/$id") {
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
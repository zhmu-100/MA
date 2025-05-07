package com.zhmu100.ma.domain.api.notes

import com.zhmu100.ma.domain.model.Note
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class NoteApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : NoteApi {
    override suspend fun getNoteById(id: String): Note {
        return client.get("$baseUrl/notes/$id").body()
    }

    override suspend fun getNotes(userId: String, page: Int, pageSize: Int): List<Note> {
        val response = client.get("$baseUrl/notes") {
            parameter("user_id", userId)
            parameter("page", page)
            parameter("page_size", pageSize)
        }

        return if (response.status.isSuccess()) {
            response.body()
        } else {
            emptyList()
        }
    }

    override suspend fun createNote(userId: String, title: String, content: String): Note {
        return client.post("$baseUrl/notes") {
            contentType(ContentType.Application.Json)
            setBody(Note(userId = userId, title = title, content = content))
        }.body()
    }

    override suspend fun updateNote(id: String, userId: String, title: String, content: String): Note {
        return client.put("$baseUrl/notes/$id") {
            contentType(ContentType.Application.Json)
            setBody(Note(userId = userId, title = title, content = content))
        }.body()
    }

    override suspend fun deleteNote(id: String, userId: String): String {
        return client.delete("$baseUrl/notes/$id"){
            parameter("user_id", userId)
        }.body()
    }
}

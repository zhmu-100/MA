package com.zhmu100.ma.domain.api.notes

import com.zhmu100.ma.domain.model.Note
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class NoteApiImpl(private val client: HttpClient) : NoteApi {
    private val GATEWAY_BASE= "http://localhost:8080/api/notebook";

    override suspend fun getNoteById(id: String): Note {
        return client.get("${GATEWAY_BASE}/$id").body()
    }

    override suspend fun getNotes(userId: String, page: Int, pageSize: Int): List<Note> {
        return client.get("${GATEWAY_BASE}/notes") {
            parameter("user_id", userId)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    override suspend fun createNote(userId: String, title: String, content: String): Note {
        return client.post("${GATEWAY_BASE}/notes") {
            contentType(ContentType.Application.Json)
            setBody(Note(userId = userId, title = title, content = content))
        }.body()
    }

    override suspend fun updateNote(id: String, title: String, content: String): Note {
        return client.put("${GATEWAY_BASE}/notes/$id") {
            contentType(ContentType.Application.Json)
            setBody(Note(id = id, title = title, content = content))
        }.body()
    }

    override suspend fun deleteNote(id: String): String {
        return client.delete("${GATEWAY_BASE}/notes/$id").body()
    }
}

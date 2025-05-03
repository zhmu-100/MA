package com.zhmu100.ma.domain.api.notes

import com.zhmu100.ma.domain.model.Note

interface NoteApi {
    suspend fun getNoteById(id: String): Note
    suspend fun getNotes(userId: String, page: Int, pageSize: Int): List<Note>
    suspend fun createNote(userId: String, title: String, content: String): Note
    suspend fun updateNote(id: String, title: String, content: String): Note
    suspend fun deleteNote(id: String): String
}

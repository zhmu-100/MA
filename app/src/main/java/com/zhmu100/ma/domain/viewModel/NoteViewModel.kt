package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.notes.NoteApiImpl
import com.zhmu100.ma.domain.model.Note
import com.zhmu100.ma.domain.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {
    private val api = NoteApiImpl(Network.httpClient)

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun getNoteById(id: String) {
        viewModelScope.launch {
            runCatching {
                api.getNoteById(id)
            }.onSuccess {
                _note.value = it
                _message.value = "Note got"
            }.onFailure {
                _message.value = "Get note error: ${it.message}"
            }
        }
    }

    fun getNotes(userId: String, page: Int, pageSize: Int) {
        viewModelScope.launch {
            runCatching {
                api.getNotes(userId, page, pageSize)
            }.onSuccess {
                _notes.value = it
                _message.value = "Notes got"
            }.onFailure {
                _message.value = "Notes got error: ${it.message}"
            }
        }
    }

    fun createNote(userId: String, title: String, content: String) {
        if (!isValidFields(title, content)) {
            _message.value = "Fields must not be empty"
            return
        }

        viewModelScope.launch {
            runCatching {
                api.createNote(userId, title, content)
            }.onSuccess {
                _note.value = it
                _message.value = "Note created"
            }.onFailure {
                _message.value = "Note creating error: ${it.message}"
            }
        }
    }

    fun updateNote(id: String, title: String, content: String) {
        if (!isValidFields(title, content)) {
            _message.value = "Fields must not be empty"
            return
        }

        viewModelScope.launch {
            runCatching {
                api.updateNote(id, title, content)
            }.onSuccess {
                _note.value = it
                _message.value = "Note updated"
            }.onFailure {
                _message.value = "Note updating error: ${it.message}"
            }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            runCatching {
                api.deleteNote(id)
            }.onSuccess {
                _message.value = "Note deleted"
            }.onFailure {
                _message.value = "Note deleting error: ${it.message}"
            }
        }
    }

    private fun isValidFields(title: String, content: String): Boolean {
        return title.isNotBlank() && content.isNotBlank()
    }
}

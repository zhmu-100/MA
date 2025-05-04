package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.notes.NoteApi
import com.zhmu100.ma.domain.model.Note
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteApi: NoteApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _notesState = MutableStateFlow<ViewState<List<Note>>>(ViewState.Uninitialized)
    val notesState = _notesState.asStateFlow()

    private val _currentNoteState = MutableStateFlow<ViewState<Note>>(ViewState.Uninitialized)
    val currentNoteState = _currentNoteState.asStateFlow()

    fun getNoteById(id: String) {
        if (_currentNoteState.value is ViewState.Loading) return

        _currentNoteState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                noteApi.getNoteById(id)
            }.onSuccess { note ->
                _currentNoteState.value = ViewState.Success(note)
            }.onFailure {
                _currentNoteState.value = ViewState.Error("Get note error: ${it.message}", it)
            }
        }
    }

    fun listNotes(page: Int = 1, pageSize: Int = 10) {
        if (_notesState.value is ViewState.Loading) return

        _notesState.value = ViewState.Loading
        val userId = tokenStorage.getUserId()

        viewModelScope.launch {
            runCatching {
                noteApi.getNotes(userId, page, pageSize)
            }.onSuccess { notes ->
                _notesState.value = ViewState.Success(notes)
            }.onFailure {
                _notesState.value = ViewState.Error("Error to load notes: ${it.message}", it)
            }
        }
    }

    fun createNote(title: String, content: String) {
        if (_currentNoteState.value is ViewState.Loading) return

        _currentNoteState.value = ViewState.Loading
        val userId = tokenStorage.getUserId()

        viewModelScope.launch {
            runCatching {
                noteApi.createNote(userId, title, content)
            }.onSuccess { createdNote ->
                _currentNoteState.value = ViewState.Success(createdNote, "Note created")
            }.onFailure {
                _currentNoteState.value = ViewState.Error("Note creating error: ${it.message}", it)
            }
        }
    }

    fun updateNote(id: String, title: String, content: String) {
        if (_currentNoteState.value is ViewState.Loading) return

        _currentNoteState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                noteApi.updateNote(id, title, content)
            }.onSuccess { updatedNote ->
                _currentNoteState.value = ViewState.Success(updatedNote, "Note updated")
            }.onFailure {
                _currentNoteState.value = ViewState.Error("Note updating error: ${it.message}", it)
            }
        }
    }

    fun deleteNote(id: String) {
        if (_currentNoteState.value is ViewState.Loading) return

        _currentNoteState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                noteApi.deleteNote(id)
                _currentNoteState.value = ViewState.Success(Note(id = ""), "Note deleted")
            }.onFailure {
                _currentNoteState.value = ViewState.Error("Note deleting error: ${it.message}", it)
            }
        }
    }

    fun clearCurrentNote() {
        _currentNoteState.value = ViewState.Uninitialized
    }

    fun clearErrors() {
        _notesState.value = ViewState.Uninitialized
        _currentNoteState.value = ViewState.Uninitialized
    }
}
package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.comments.CommentApi
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.posts.Comment
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentApi: CommentApi,
    private val profileApi: ProfileApi,
    private val tokenStorage: TokenStorage
    ) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    private val _commentError = MutableStateFlow<String?>(null)
    val commentError: StateFlow<String?> = _commentError

    private val _usernames = MutableStateFlow<Map<String, String>>(emptyMap())
    val usernames: StateFlow<Map<String, String>> = _usernames


    fun loadComments(postId: String?, page: Int = 1, pageSize: Int = 50) {
        viewModelScope.launch {
            runCatching {
                commentApi.listComments(postId, page, pageSize)
            }.onSuccess {
                _comments.value = it
            }.onFailure {
                _commentError.value = "Error: ${it.message}"
            }
        }
    }

    fun createComment(postId: String, content: String) {
        viewModelScope.launch {
            runCatching {
                val userId = tokenStorage.getUserId()
                commentApi.createComment(postId, userId, content)
            }.onSuccess { newComment ->
                _comments.value = listOf(newComment) + _comments.value
            }.onFailure {
                _commentError.value = "Error: ${it.message}"
            }
        }
    }

    fun loadUsernames() {
        viewModelScope.launch {
            val currentMap = _usernames.value.toMutableMap()
            val ids = _comments.value.map { it.userId }.distinct()
            for (id in ids) {
                if (!currentMap.containsKey(id)) {
                    val profile = profileApi.getProfileById(id)
                    currentMap[id] = profile.name
                }
            }
            _usernames.value = currentMap
        }
    }

}

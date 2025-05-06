package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.reactions.ReactionApi
import com.zhmu100.ma.domain.model.posts.PostReaction
import com.zhmu100.ma.domain.model.posts.Reaction
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReactionViewModel(
    private val reactionApi: ReactionApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _reactionState = MutableStateFlow<PostReaction?>(null)
    val reactionState: StateFlow<PostReaction?> = _reactionState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addReaction(postId: String?, reaction: Reaction) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            runCatching {
                val userId = tokenStorage.getUserId()
                reactionApi.addReaction(postId, userId, reaction)
            }.onSuccess { result ->
                _reactionState.value = result
            }.onFailure { e ->
                _error.value = e.message
            }

            _loading.value = false
        }
    }

    fun removeReaction(postId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            runCatching {
                val userId=tokenStorage.getUserId()
                reactionApi.removeReaction(postId, userId)
            }.onSuccess {
                _reactionState.value = null
            }.onFailure { e ->
                _error.value = e.message
            }

            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}

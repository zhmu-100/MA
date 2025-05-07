package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.profile.ListFollowersResponse
import com.zhmu100.ma.domain.model.profile.ListFollowingResponse
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowerViewModel(
    private val profileApi: ProfileApi,
    private val tokenStorage: TokenStorage
    ) : ViewModel() {
    private val _followResult = MutableStateFlow<Result<Boolean>?>(null)
    val followResult: StateFlow<Result<Boolean>?> = _followResult

    private val _followers = MutableStateFlow<ListFollowersResponse?>(null)
    val followers: StateFlow<ListFollowersResponse?> = _followers

    private val _following = MutableStateFlow<ListFollowingResponse?>(null)
    val following: StateFlow<ListFollowingResponse?> = _following

    fun follow(followeeId: String) {
        viewModelScope.launch {
            val result = runCatching {
                profileApi.follow(followeeId)
            }
            result.onSuccess {
                _followResult.value = Result.success(true)
            }.onFailure { exception ->
                _followResult.value = Result.failure(exception)
            }
        }
    }

    suspend fun unfollow(followeeId: String) {
        runCatching {
            profileApi.unfollow(followeeId)
        }.onSuccess {
            _followResult.value = Result.success(false)
        }.onFailure { exception ->
            _followResult.value = Result.failure(exception)
        }
    }

    // Метод для получения списка подписчиков с пагинацией
    suspend fun listFollowers(userId: String, page: Int, pageSize: Int) {
        val result = runCatching {
            profileApi.listFollowers(userId, page, pageSize)
        }
        result.onSuccess { response ->
            _followers.value = response
        }.onFailure { exception ->
            _followers.value = null
        }
    }

    // Метод для получения списка подписок с пагинацией
    suspend fun listFollowing(page: Int, pageSize: Int) {
        val userId = tokenStorage.getUserId()
        val result = runCatching {
            profileApi.listFollowing(userId, page, pageSize)
        }
        result.onSuccess { response ->
            _following.value = response
        }.onFailure { exception ->
            _following.value = null
        }
    }
}
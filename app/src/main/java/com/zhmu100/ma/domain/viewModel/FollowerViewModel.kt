package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.profile.ProfileApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowerViewModel(private val profileApi: ProfileApi) : ViewModel() {
    private val _followResult = MutableStateFlow<Result<Boolean>?>(null)
    val followResult: StateFlow<Result<Boolean>?> = _followResult

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
}
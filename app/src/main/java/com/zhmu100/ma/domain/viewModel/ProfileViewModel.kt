package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileApi: ProfileApi
) : ViewModel() {
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Uninitialized)
    val profileState = _profileState.asStateFlow()

    fun loadProfile(forceRefresh: Boolean = false) {
        if (_profileState.value is ProfileState.Loading && !forceRefresh) {
            return
        }

        _profileState.value = ProfileState.Loading

        viewModelScope.launch {
            runCatching {
                profileApi.getMyProfile()
            }.onSuccess {
                _profileState.value = ProfileState.Success(it)
            }.onFailure {
                _profileState.value = ProfileState.Error("Error loading profile ${it.message}", it)
            }
        }
    }

    fun updateProfile(profile: UserProfile) {
        if (_profileState.value is ProfileState.Loading) {
            return
        }

        _profileState.value = ProfileState.Loading

        viewModelScope.launch {
            runCatching {
                profileApi.updateProfile(profile.id, profile)
            }.onSuccess {
                _profileState.value = ProfileState.Success(it)
            }.onFailure {
                _profileState.value = ProfileState.Error("Error loading profile ${it.message}", it)
            }
        }
    }

    fun clearError() {
        if (_profileState.value is ProfileState.Error) {
            _profileState.value = ProfileState.Uninitialized
        }
    }

    sealed class ProfileState {
        data object Uninitialized : ProfileState()
        data object Loading : ProfileState()
        data class Success(val profile: UserProfile) : ProfileState()
        data class Error(val message: String, val throwable: Throwable? = null) : ProfileState()
    }
}
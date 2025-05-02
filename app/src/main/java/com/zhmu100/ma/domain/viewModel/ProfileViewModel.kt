package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileApi: ProfileApi,
    private val filesApi: FilesApi
) : ViewModel() {
    private val _profileState = MutableStateFlow<ViewState<UserProfile>>(ViewState.Uninitialized)
    val profileState = _profileState.asStateFlow()

    private val _profilePhotoUrl = MutableStateFlow<String?>(null)
    val profilePhotoUrl = _profilePhotoUrl.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile(forceRefresh: Boolean = false) {
        if (_profileState.value is ViewState.Loading && !forceRefresh) {
            return
        }

        _profileState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                val profile = profileApi.getMyProfile()
                profile to (profile.imageId?.let { filesApi.getFileUrl(it) })
            }.onSuccess { (profile, photoUrl) ->
                _profileState.value = ViewState.Success(profile, "Profile loaded")
                _profilePhotoUrl.value = photoUrl
            }.onFailure {
                _profileState.value = ViewState.Error("Error loading profile ${it.message}", it)
                _profilePhotoUrl.value = null
            }
        }
    }

    fun updateProfile(profile: UserProfile) {
        if (_profileState.value is ViewState.Loading) {
            return
        }

        _profileState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                profileApi.updateProfile(profile.id, profile)
            }.onSuccess {
                _profileState.value = ViewState.Success(it)
            }.onFailure {
                _profileState.value = ViewState.Error("Error loading profile ${it.message}", it)
            }
        }
    }

    fun updateProfilePhoto(file: ByteArray, fileName: String, mimeType: String) {
        if (_profileState.value is ViewState.Loading) {
            return
        }

        _profileState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                val currentProfile = when (val state = _profileState.value) {
                    is ViewState.Success -> state.data
                    else -> profileApi.getMyProfile()
                }

                val (resultProfile, photoUrl) = if (currentProfile.imageId == null) {
                    val fileId = filesApi.uploadFile(file, fileName, mimeType)
                    val updatedProfile = currentProfile.copy(imageId = fileId)
                    val savedProfile = profileApi.updateProfile(updatedProfile.id, updatedProfile)
                    savedProfile to filesApi.getFileUrl(fileId)
                } else {
                    filesApi.fixUpload(currentProfile.imageId, file, fileName, mimeType)
                    val updatedProfile = profileApi.getMyProfile()
                    updatedProfile to filesApi.getFileUrl(currentProfile.imageId)
                }

                resultProfile to photoUrl
            }.onSuccess { (updatedProfile, photoUrl) ->
                _profileState.value = ViewState.Success(updatedProfile, "Profile picture uploaded")
                _profilePhotoUrl.value = photoUrl
            }.onFailure {
                _profileState.value =
                    ViewState.Error("Error updating profile photo: ${it.message}", it)
            }
        }
    }

    fun clearError() {
        if (_profileState.value is ViewState.Error) {
            _profileState.value = ViewState.Uninitialized
        }
    }
}
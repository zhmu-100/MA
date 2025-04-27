package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import com.zhmu100.ma.domain.storage.SettingsStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(private val settingsStorage: SettingsStorage) : ViewModel() {
    private val _language = MutableStateFlow(settingsStorage.getAppLanguage())
    val language: StateFlow<SettingsStorage.AppLanguage> = _language.asStateFlow()

    private val _theme = MutableStateFlow(settingsStorage.getAppTheme())
    val theme: StateFlow<SettingsStorage.AppTheme> = _theme.asStateFlow()

    fun setLanguage(language: SettingsStorage.AppLanguage) {
        settingsStorage.setAppLanguage(language)
        _language.value = language
    }

    fun setTheme(theme: SettingsStorage.AppTheme) {
        settingsStorage.setAppTheme(theme)
        _theme.value = theme
    }
}
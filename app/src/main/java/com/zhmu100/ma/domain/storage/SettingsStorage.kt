package com.zhmu100.ma.domain.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Хранилище для локальных пользовательских настроек приложения
 */
object SettingsStorage {
    private const val PREF_NAME = "app_settings"
    private const val KEY_LANGUAGE = "app_language"
    private const val KEY_THEME = "app_theme"

    // Значения по умолчанию
    private const val DEFAULT_LANGUAGE = "ru"
    private const val DEFAULT_THEME = "light"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Язык
    private fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    private fun setLanguage(language: String) {
        prefs.edit { putString(KEY_LANGUAGE, language) }
    }

    // Тема
    private fun getTheme(): String {
        return prefs.getString(KEY_THEME, DEFAULT_THEME) ?: DEFAULT_THEME
    }

    private fun setTheme(theme: String) {
        prefs.edit { putString(KEY_THEME, theme) }
    }

    // Для удобства - enum-подобные методы
    enum class AppLanguage(val displayName: String) { RUSSIAN("Русский"), ENGLISH("English") }
    enum class AppTheme { LIGHT, DARK }

    fun getAppLanguage(): AppLanguage {
        return when (getLanguage()) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.RUSSIAN
        }
    }

    fun setAppLanguage(language: AppLanguage) {
        setLanguage(
            when (language) {
                AppLanguage.ENGLISH -> "en"
                AppLanguage.RUSSIAN -> "ru"
            }
        )
    }

    fun getAppTheme(): AppTheme {
        return when (getTheme()) {
            "dark" -> AppTheme.DARK
            else -> AppTheme.LIGHT
        }
    }

    fun setAppTheme(theme: AppTheme) {
        setTheme(
            when (theme) {
                AppTheme.DARK -> "dark"
                AppTheme.LIGHT -> "light"
            }
        )
    }
}
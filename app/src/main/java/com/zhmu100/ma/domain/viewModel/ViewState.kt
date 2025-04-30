package com.zhmu100.ma.domain.viewModel

/**
 * Состояние объекта во ViewModel для уведосления UI о прогрессе и результате выполнения запросов
 */
sealed class ViewState<out T> {
    data object Uninitialized : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T, val message: String = "") : ViewState<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : ViewState<Nothing>()
}
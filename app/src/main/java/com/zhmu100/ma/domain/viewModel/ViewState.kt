package com.zhmu100.ma.domain.viewModel

sealed class ViewState<out T> {
    data object Uninitialized : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T, val message: String = "") : ViewState<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : ViewState<Nothing>()
}
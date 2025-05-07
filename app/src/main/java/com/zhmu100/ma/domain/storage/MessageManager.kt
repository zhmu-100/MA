package com.zhmu100.ma.domain.storage

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class MessageManager {
    private val _messages = MutableSharedFlow<String>()
    val messages = _messages.asSharedFlow()

    suspend fun emitMessage(message: String) {
        _messages.emit(message)
    }
}
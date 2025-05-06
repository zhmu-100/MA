package com.zhmu100.ma.domain.api.auth

import com.zhmu100.ma.domain.model.AuthResponse
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.model.RegisterRequest


interface AuthApi {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
    suspend fun logout()
    suspend fun refresh(refreshToken: String): AuthResponse
    suspend fun validate(): Boolean
}

package com.zhmu100.ma.domain.api.auth

import com.zhmu100.ma.domain.model.AuthResponse
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.model.RegisterRequest
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApiImpl(private val client: HttpClient) : AuthApi {
    private val GATEWAY_BASE= "http://localhost:8080/api/auth";

    override suspend fun login(request: LoginRequest): AuthResponse {
        return client.post("${GATEWAY_BASE}/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        return client.post("${GATEWAY_BASE}/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun logout() {
        client.post("${GATEWAY_BASE}/logout")
    }

    override suspend fun refresh(): AuthResponse {
        return client.post("${GATEWAY_BASE}/refresh").body()
    }

    override suspend fun validate(): Boolean {
        return client.get("${GATEWAY_BASE}/validate").status == HttpStatusCode.OK
    }
}

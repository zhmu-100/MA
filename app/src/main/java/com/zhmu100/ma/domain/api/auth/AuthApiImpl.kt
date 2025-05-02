package com.zhmu100.ma.domain.api.auth

import com.zhmu100.ma.domain.model.AuthResponse
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.model.RegisterRequest
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : AuthApi {
    override suspend fun login(request: LoginRequest): AuthResponse {
        return client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        return client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun logout() {
        client.post("$baseUrl/logout")
    }

    override suspend fun refresh(): AuthResponse {
        return client.post("$baseUrl/refresh").body()
    }

    override suspend fun validate(): Boolean {
        return client.get("$baseUrl/validate").status == HttpStatusCode.OK
    }
}

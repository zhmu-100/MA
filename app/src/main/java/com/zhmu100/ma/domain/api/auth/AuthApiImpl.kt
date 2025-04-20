package com.zhmu100.ma.domain.api.auth

import com.zhmu100.ma.domain.model.AuthResponse
import com.zhmu100.ma.domain.model.LoginRequest
import com.zhmu100.ma.domain.model.RegisterRequest
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApiImpl(private val client: HttpClient) : AuthApi {
    private val AUTH_SERVICE_URL= "http://auth-service:8081/auth";

    override suspend fun login(request: LoginRequest): AuthResponse {
        return client.post("${AUTH_SERVICE_URL}/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        return client.post("${AUTH_SERVICE_URL}/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun logout() {
        client.post("${AUTH_SERVICE_URL}/logout")
    }

    override suspend fun refresh(): AuthResponse {
        return client.post("${AUTH_SERVICE_URL}/refresh").body()
    }

    override suspend fun validate(): Boolean {
        return client.get("${AUTH_SERVICE_URL}/validate").status == HttpStatusCode.OK
    }
}

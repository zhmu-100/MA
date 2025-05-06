package com.zhmu100.ma.domain

import com.zhmu100.ma.domain.api.auth.AuthApiImpl
import com.zhmu100.ma.domain.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Настройка клиента ktor
 */

object Network {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            level = LogLevel.BODY
        }

        engine {
            requestTimeout = 15_000
            endpoint {
                connectTimeout = 10_000
                socketTimeout = 10_000
            }
        }

        install(HttpTimeout)

        defaultRequest {
            val accessToken = TokenStorage.getAccessToken()
            if (!accessToken.isNullOrBlank()) {
                headers.append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }
    }
}

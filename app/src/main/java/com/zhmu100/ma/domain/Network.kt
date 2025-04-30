package com.zhmu100.ma.domain

import com.zhmu100.ma.domain.storage.TokenStorage
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
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

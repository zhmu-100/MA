package com.zhmu100.ma.domain.api.profile

import com.zhmu100.ma.domain.model.profile.FollowRequest
import com.zhmu100.ma.domain.model.profile.ListFollowersResponse
import com.zhmu100.ma.domain.model.profile.ListFollowingResponse
import com.zhmu100.ma.domain.model.profile.ListProfilesResponse
import com.zhmu100.ma.domain.model.profile.UserProfile
import com.zhmu100.ma.domain.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProfileApiImpl(
    private val client: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : ProfileApi {
    override suspend fun getMyProfile(): UserProfile {
        return getProfileById(tokenStorage.getUserId())
    }

    override suspend fun getProfileById(id: String): UserProfile {
        return client.get("$baseUrl/$id").body()
    }

    override suspend fun listProfiles(page: Int, pageSize: Int): ListProfilesResponse {
        return client.get(baseUrl) {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    override suspend fun createProfile(profile: UserProfile): UserProfile {
        val p = profile.copy(user_id = tokenStorage.getUserId())
        return client.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("profile" to p))
        }.body()
    }

    override suspend fun updateProfile(id: String, profile: UserProfile): UserProfile {
        return client.put("$baseUrl/$id") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("profile" to profile))
        }.body()
    }

    override suspend fun deleteProfile(id: String) {
        client.delete("$baseUrl/$id")
    }

    override suspend fun follow(followeeId: String) {
        val followerId = tokenStorage.getUserId()
        client.post("$baseUrl/$followeeId/follow") {
            contentType(ContentType.Application.Json)
            setBody(FollowRequest(followerId, followeeId))
        }
    }


    override suspend fun unfollow(followeeId: String) {
        client.post("$baseUrl/$followeeId/unfollow") {
            parameter("follower_id", tokenStorage.getUserId())
            parameter("follower_id", followeeId)
        }
    }

    override suspend fun listFollowers(
        userId: String,
        page: Int,
        pageSize: Int
    ): ListFollowersResponse {
        return client.get("$baseUrl/$userId/followers") {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    override suspend fun listFollowing(
        userId: String,
        page: Int,
        pageSize: Int
    ): ListFollowingResponse {
        return client.get("$baseUrl/$userId/following") {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }
}
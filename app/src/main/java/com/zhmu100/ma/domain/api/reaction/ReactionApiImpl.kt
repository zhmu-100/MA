package com.zhmu100.ma.domain.api.reaction

import com.zhmu100.ma.domain.api.reactions.ReactionApi
import com.zhmu100.ma.domain.model.posts.PostReaction
import com.zhmu100.ma.domain.model.posts.Reaction
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class ReactionApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : ReactionApi {

    override suspend fun addReaction(postId: String?, userId: String, reaction: Reaction): PostReaction {
        return client.post("$baseUrl/posts/$postId/reactions") {
            contentType(ContentType.Application.Json)
            setBody(AddReactionRequest(postId, userId, reaction))
        }.body()
    }

    override suspend fun removeReaction(postId: String, userId: String) {
        client.request("$baseUrl/posts/$postId/reactions") {
            method = HttpMethod.Delete
            contentType(ContentType.Application.Json)
            setBody(RemoveReactionRequest(postId, userId))
        }
    }

    @Serializable
    data class AddReactionRequest(val postId: String?, val userId: String, val reaction: Reaction)

    @Serializable
    data class RemoveReactionRequest(val postId: String, val userId: String)
}

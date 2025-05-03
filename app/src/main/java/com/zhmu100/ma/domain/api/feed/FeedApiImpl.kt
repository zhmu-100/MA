package com.zhmu100.ma.domain.api.feed

import com.zhmu100.ma.domain.model.feed.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class FeedApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : FeedApi {

    override suspend fun listPosts(viewerId: String, page: Int, pageSize: Int): List<Post> {
        return client.get("$baseUrl/posts") {
            parameter("viewer_id", viewerId)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    override suspend fun listUserPosts(userId: String, viewerId: String, page: Int, pageSize: Int): List<Post> {
        return client.get("$baseUrl/users/$userId/posts") {
            parameter("viewer_id", viewerId)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    override suspend fun getPost(id: String): Post {
        return client.get("$baseUrl/posts/$id").body()
    }

    override suspend fun createPost(post: Post): Post {
        return client.post("$baseUrl/posts") {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()
    }

    override suspend fun createComment(postId: String, comment: PostComment): PostComment {
        return client.post("$baseUrl/posts/$postId/comments") {
            contentType(ContentType.Application.Json)
            setBody(comment)
        }.body()
    }

    override suspend fun listComments(postId: String, page: Int, pageSize: Int): List<PostComment> {
        return client.get("$baseUrl/posts/$postId/comments") {
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()
    }

    override suspend fun addReaction(postId: String, userId: String, reaction: Reaction): PostReaction {
        return client.post("$baseUrl/posts/$postId/reactions") {
            contentType(ContentType.Application.Json)
            setBody(PostReaction(postId, userId, reaction))
        }.body()
    }

    override suspend fun removeReaction(postId: String, userId: String) {
        client.delete("$baseUrl/posts/$postId/reactions") {
            parameter("user_id", userId)
        }
    }
}

package com.zhmu100.ma.domain.api.comments

import com.zhmu100.ma.domain.model.posts.Comment
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class CommentApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : CommentApi {
    override suspend fun createComment(postId: String, userId: String, content: String): Comment {
        return client.post("${baseUrl}/posts/$postId/comments") {
            contentType(ContentType.Application.Json)
            setBody(CreateCommentRequest(userId, content))
        }.body()
    }

    override suspend fun listComments(postId: String?, page: Int, pageSize: Int): List<Comment> {
        return client.post("${baseUrl}/$postId/comments") {
            contentType(ContentType.Application.Json)
            setBody(ListCommentsRequest(page, pageSize))
        }.body<ListCommentsResponse>().comments
    }

    @Serializable
    data class CreateCommentRequest(val userId: String, val content: String)

    @Serializable
    data class ListCommentsRequest(val page: Int, val pageSize: Int)

    @Serializable
    data class ListCommentsResponse(val comments: List<Comment>)
}

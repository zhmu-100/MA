package com.zhmu100.ma.domain.api.feed

import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.model.posts.CreatePostRequest
import com.zhmu100.ma.domain.model.posts.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostApiImpl(
    private val filesApi: FilesApi,
    private val client: HttpClient,
    private val baseUrl: String
) : PostApi {

    override suspend fun createPost(post: Post): Post {
        return client.post("$baseUrl/posts") {
            contentType(ContentType.Application.Json)
            setBody(CreatePostRequest(post))
        }.body()
    }

    override suspend fun getPostById(postId: String): Post {
        return client.get("$baseUrl/posts/$postId").body()
    }

    override suspend fun listPosts(page: Int, pageSize: Int): List<Post> {
        return client.get("$baseUrl/posts?page=$page&page_size=$pageSize").body<List<Post>>()
    }

    override suspend fun listUserPosts(userId: String, page: Int, pageSize: Int): List<Post> {
        return client.get("$baseUrl/posts/user/$userId?page=$page&page_size=$pageSize").body<List<Post>>()
    }
}
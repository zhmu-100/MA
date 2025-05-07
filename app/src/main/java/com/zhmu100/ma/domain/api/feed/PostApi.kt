package com.zhmu100.ma.domain.api.feed

import com.zhmu100.ma.domain.model.posts.Post
import com.zhmu100.ma.domain.model.posts.PostsResponse

interface PostApi {
    suspend fun createPost(post: Post): Post
    suspend fun getPostById(postId: String): Post
    suspend fun listPosts(page: Int, pageSize: Int):PostsResponse
    suspend fun listUserPosts(userId: String, page: Int, pageSize: Int): PostsResponse
}


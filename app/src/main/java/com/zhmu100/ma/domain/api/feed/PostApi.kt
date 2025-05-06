package com.zhmu100.ma.domain.api.feed

import com.zhmu100.ma.domain.model.posts.Post

interface PostApi {
    suspend fun createPost(post: Post): Post
    suspend fun getPostById(postId: String): Post
    suspend fun listPosts(page: Int, pageSize: Int): List<Post>
    suspend fun listUserPosts(userId: String, page: Int, pageSize: Int): List<Post>
}
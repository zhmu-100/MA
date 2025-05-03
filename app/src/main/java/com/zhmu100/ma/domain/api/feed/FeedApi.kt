package com.zhmu100.ma.domain.api.feed

import com.zhmu100.ma.domain.model.feed.*

interface FeedApi {
    suspend fun listPosts(viewerId: String, page: Int, pageSize: Int): List<Post>
    suspend fun listUserPosts(userId: String, viewerId: String, page: Int, pageSize: Int): List<Post>
    suspend fun getPost(id: String): Post
    suspend fun createPost(post: Post): Post
    suspend fun createComment(postId: String, comment: PostComment): PostComment
    suspend fun listComments(postId: String, page: Int, pageSize: Int): List<PostComment>
    suspend fun addReaction(postId: String, userId: String, reaction: Reaction): PostReaction
    suspend fun removeReaction(postId: String, userId: String)
}

package com.zhmu100.ma.domain.api.comments

import com.zhmu100.ma.domain.model.posts.Comment

interface CommentApi {
    suspend fun createComment(postId: String, userId: String, content: String): Comment
    suspend fun listComments(postId: String?, page: Int = 1, pageSize: Int = 50): List<Comment>
}

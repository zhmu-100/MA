package com.zhmu100.ma.domain.api.reactions

import com.zhmu100.ma.domain.model.posts.PostReaction
import com.zhmu100.ma.domain.model.posts.Reaction

interface ReactionApi {
    suspend fun addReaction(postId: String?, userId: String, reaction: Reaction): PostReaction
    suspend fun removeReaction(postId: String, userId: String)
}

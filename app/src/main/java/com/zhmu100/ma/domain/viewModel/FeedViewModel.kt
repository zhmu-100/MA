package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.feed.FeedApi
import com.zhmu100.ma.domain.model.feed.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import java.time.Instant
import com.zhmu100.ma.domain.utils.DateTimeUtils

class FeedViewModel(
    private val feedApi: FeedApi
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _selectedPost = MutableStateFlow<Post?>(null)
    val selectedPost = _selectedPost.asStateFlow()

    private val _comments = MutableStateFlow<List<PostComment>>(emptyList())
    val comments = _comments.asStateFlow()

    fun loadFeedPosts(viewerId: String, page: Int = 1, pageSize: Int = 20) {
        viewModelScope.launch {
            runCatching {
                _posts.value = feedApi.listPosts(viewerId, page, pageSize)
            }
        }
    }

    fun loadUserPosts(userId: String, viewerId: String, page: Int = 1, pageSize: Int = 20) {
        viewModelScope.launch {
            runCatching {
                _posts.value = feedApi.listUserPosts(userId, viewerId, page, pageSize)
            }
        }
    }

    fun getPost(id: String) {
        viewModelScope.launch {
            runCatching {
                _selectedPost.value = feedApi.getPost(id)
            }
        }
    }

    fun createPost(userId: String, content: String?, attachments: List<PostAttachment>) {
        viewModelScope.launch {
            val now = Instant.now()
            val post = Post(
                id = UUID.randomUUID().toString(),
                user_id = userId,
                content = content,
                attachments = attachments,
                date = DateTimeUtils.instantToIsoString(now)
            )
            runCatching {
                val created = feedApi.createPost(post)
                _posts.value = listOf(created) + _posts.value
            }
        }
    }

    fun createComment(postId: String, userId: String, content: String) {
        viewModelScope.launch {
            val now = Instant.now()
            val comment = PostComment(
                id = UUID.randomUUID().toString(),
                user_id = userId,
                content = content,
                date = DateTimeUtils.instantToIsoString(now)
            )
            runCatching {
                val created = feedApi.createComment(postId, comment)
                _comments.value = listOf(created) + _comments.value
            }
        }
    }

    fun loadComments(postId: String, page: Int = 1, pageSize: Int = 20) {
        viewModelScope.launch {
            runCatching {
                _comments.value = feedApi.listComments(postId, page, pageSize)
            }
        }
    }

    fun addReaction(postId: String, userId: String, reaction: Reaction) {
        viewModelScope.launch {
            runCatching {
                feedApi.addReaction(postId, userId, reaction)
                getPost(postId)
            }
        }
    }

    fun removeReaction(postId: String, userId: String) {
        viewModelScope.launch {
            runCatching {
                feedApi.removeReaction(postId, userId)
                getPost(postId)
            }
        }
    }
}

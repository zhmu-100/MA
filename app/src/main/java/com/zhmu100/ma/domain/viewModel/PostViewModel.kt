package com.zhmu100.ma.domain.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.feed.PostApi
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.model.posts.AttachmentType
import com.zhmu100.ma.domain.model.posts.Post
import com.zhmu100.ma.domain.model.posts.Attachment
import com.zhmu100.ma.domain.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class PostViewModel(
    private val filesApi: FilesApi,
    private val postApi: PostApi,
    private val tokenStorage: TokenStorage,
    private val profileApi: ProfileApi
) : ViewModel() {
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()

    private val _postState = MutableStateFlow<ViewState<Unit>>(ViewState.Uninitialized)
    val postState = _postState.asStateFlow()

    private val _postDetailsState = MutableStateFlow<ViewState<Post>>(ViewState.Uninitialized)
    val postDetailsState = _postDetailsState.asStateFlow()

    private val _postsListState = MutableStateFlow<ViewState<List<Post>>>(ViewState.Uninitialized)
    val postsListState = _postsListState.asStateFlow()

    private val _userPostsListState = MutableStateFlow<ViewState<List<Post>>>(ViewState.Uninitialized)
    val userPostsListState = _userPostsListState.asStateFlow()

    private val _fileContent = MutableStateFlow<ByteArray?>(null)
    val fileContent = _fileContent.asStateFlow()

    private val _fileState = MutableStateFlow<ViewState<ByteArray>>(ViewState.Uninitialized)
    val fileState = _fileState.asStateFlow()

    private val _usernames = mutableStateOf<Map<String, String>>(emptyMap())
    val usernames: State<Map<String, String>> get() = _usernames

    private val _files = mutableStateOf<Map<String, ByteArray>>(emptyMap())
    val files get() = _files

    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts get() = _posts

    fun uploadPostImage(file: ByteArray, fileName: String, mimeType: String) {
        viewModelScope.launch {
            runCatching {
//                val fileId = filesApi.uploadFile(file, fileName, mimeType)
//                filesApi.getFileUrl(fileId)

            }.onSuccess { imageUrl ->
//                _imageUrl.value = imageUrl
            }.onFailure {
                _imageUrl.value = null
            }
        }
    }

    fun createPost(text: String) {
        if (_postState.value is ViewState.Loading) return

        _postState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                val image = imageUrl.value
                val userId = tokenStorage.getUserId()
                val postContent = text

                val attachments = image?.let {
                    listOf(
                        Attachment(
                            type = AttachmentType.ATTACHMENT_TYPE_IMAGE,
                            position = 0,
                            minioId = it
                        )
                    )
                } ?: emptyList()

                val post = Post(
                    userId = userId,
                    content = postContent,
                    attachments = attachments
                )

                postApi.createPost(post)
            }.onSuccess {
                _postState.value = ViewState.Success(Unit, "Post created")
                _imageUrl.value = null
            }.onFailure {
                _postState.value = ViewState.Error("Error: ${it.message}", it)
            }
        }
    }

    fun getPostById(postId: String) {
        if (_postDetailsState.value is ViewState.Loading) return

        _postDetailsState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                postApi.getPostById(postId)
            }.onSuccess {
                _postDetailsState.value = ViewState.Success(it, "post got")
            }.onFailure {
                _postDetailsState.value = ViewState.Error("Error: ${it.message}", it)
            }
        }
    }

    fun listPosts(page: Int=1, pageSize: Int=20) {
        if (_postsListState.value is ViewState.Loading) return

        _postsListState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                postApi.listPosts(page, pageSize)
            }.onSuccess { posts ->
                val posts = posts.posts
                _posts.value = posts
                _postsListState.value = ViewState.Success(posts)
                viewModelScope.launch {
                    fetchUsernames(posts)
                    fetchFiles(posts)
                }
            }.onFailure{ e ->
                Log.d(e.message, "Error: ${e.message}")
            }
        }
    }

    fun listUserPosts(page: Int=1, pageSize: Int=20) {
        if (_userPostsListState.value is ViewState.Loading) return

        _userPostsListState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                val userId = tokenStorage.getUserId()
                postApi.listUserPosts(userId, page, pageSize)
            }.onSuccess { result ->
                val posts = result.posts
                _userPostsListState.value = ViewState.Success(posts, "User posts got")
            }.onFailure {
                _userPostsListState.value = ViewState.Error("Error: ${it.message}", it)
            }
        }
    }

    fun getFileById(fileId: String) {
        if (_fileState.value is ViewState.Loading) return

        _fileState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                val fileBytes = filesApi.getFile(fileId)
                fileBytes
            }.onSuccess { fileBytes ->
                _fileContent.value = fileBytes
                _fileState.value = ViewState.Success(fileBytes, "File loaded success")
            }.onFailure { error ->
                _fileContent.value = null
                _fileState.value = ViewState.Error("Error: ${error.message}", error)
            }
        }
    }

    suspend fun fetchUsernames(posts: List<Post>) {
        val userIds = posts.map { it.userId }.distinct()
        val usernamesMap = mutableMapOf<String, String>()

        userIds.forEach { userId ->
            runCatching {
                val profile = profileApi.getProfileById(userId)
                usernamesMap[userId] = profile.name
                Log.d("FetchUsernames", "Fetched profile: ${profile.name} for $userId")
            }.onFailure {
                Log.e("FetchUsernames", "Failed to fetch for $userId: ${it.message}")
            }
        }

        _usernames.value = usernamesMap
    }

     suspend fun fetchFiles(posts: List<Post>) {
        val fileIds = posts.flatMap { it.attachments }.map { it.minioId }.distinct()
        val filesMap = mutableMapOf<String, ByteArray>()

        fileIds.forEach { fileId ->
            runCatching {
                val file = filesApi.getFile(fileId)
                filesMap[fileId] = file
            }
        }

        _files.value = filesMap
    }

    fun clearImage() {
        _imageUrl.value = null
        }
}


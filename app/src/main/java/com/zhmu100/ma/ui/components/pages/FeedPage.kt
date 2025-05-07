package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.model.posts.Reaction
import com.zhmu100.ma.domain.viewModel.CommentViewModel
import com.zhmu100.ma.domain.viewModel.FollowerViewModel
import com.zhmu100.ma.domain.viewModel.PostViewModel
import com.zhmu100.ma.domain.viewModel.ReactionViewModel
import com.zhmu100.ma.domain.viewModel.ViewState
import com.zhmu100.ma.ui.components.buttons.*
import com.zhmu100.ma.ui.components.posts.Comment
import com.zhmu100.ma.ui.components.posts.CommentsBottomSheet
import com.zhmu100.ma.ui.components.posts.PostCard
import com.zhmu100.ma.ui.components.posts.ShareBottomSheet
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    postViewModel: PostViewModel = koinViewModel(),
    commentViewModel: CommentViewModel =koinViewModel(),
    reactionViewModel: ReactionViewModel = koinViewModel(),
    followerViewModel: FollowerViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    var showComments by remember { mutableStateOf(false) }
    var showShare by remember { mutableStateOf(false) }

    val postsState = postViewModel.postsListState.collectAsState().value
    var selectedPostId by remember { mutableStateOf<String?>(null) }
    val commentState = commentViewModel.comments.collectAsState().value
//    val usernameMap = commentViewModel.usernames.collectAsState().value
    val usernameMap = postViewModel.usernames.value

    var selectedReaction by remember { mutableStateOf<Reaction?>(null) }

    LaunchedEffect(Unit) {
        postViewModel.listPosts()
    }

    LaunchedEffect(postsState) {
        val state = postsState
        if (state is ViewState.Success) {
            postViewModel.fetchUsernames(state.data)
            postViewModel.fetchFiles(state.data)
        }
    }


//    val comments = remember {
//        listOf(
//            Comment(username = "Мария", text = "Отличная тренировка!", time = "5 мин назад"),
//            Comment(username = "Иван", text = "Пост топ 🔥", time = "10 мин назад"),
//            Comment(username = "Оля", text = "Где купила коврик?)", time = "15 мин назад"),
//            Comment(username = "Антон", text = "Хочу также начать заниматься!", time = "20 мин назад")
//        )
//    }

    if (showComments && selectedPostId != null) {
        CommentsBottomSheet(
            comments = commentState.map {
                Comment(
                    username = usernameMap[it.userId]  ?: "Unknown",
                    text = it.content,
                    time = it.date
                )
            },
            onDismissRequest = { showComments = false },
            onNewCommentAdded = { newComment ->
                if (!newComment.isNullOrBlank() && selectedPostId != null) {
                commentViewModel.createComment(selectedPostId!!, newComment)
                }
            }
        )
    }

    if (showShare) {
        ShareBottomSheet(
            onDismissRequest = { showShare = false }
        )
    }

    BasePage(
        true,
        navIndex = 0,
        navController = navController,
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier.fillMaxWidth()
        ) {
            var devicesInd by remember { mutableIntStateOf(0) }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                item {
                    CategoryButton(
                        "+",
                        devicesInd == 0,
                        onClick = {
                            devicesInd = 0
                            navController?.navigate(PostScreen)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                item {
                    CategoryButton(
                        "Все",
                        devicesInd == 1,
                        onClick = { devicesInd = 1 },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                item {
                    CategoryButton(
                        "Популярное",
                        devicesInd == 2,
                        onClick = { devicesInd = 2 },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                item {
                    CategoryButton(
                        "Подписки",
                        devicesInd == 3,
                        onClick = { devicesInd = 3 },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = postsState) {
                    is ViewState.Success -> {
                        val posts = state.data
                        items(posts.size) { index ->
                            val post = posts[index]
                            PostCard(
                                userId = post.userId,
                                username = postViewModel.usernames.value[post.userId] ?: "Unknown user",
//                                postDate = post.date ?: "No date",
                                postDate = post.date?.let {
                                    try {
                                        val parsedDate = ZonedDateTime.parse(it)
                                        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
                                        parsedDate.format(formatter)
                                    } catch (e: Exception) {
                                        e.message
                                    }
                                } ?: "No date",
                                postText = post.content ?: "No content",
                                isSubscribed = false,
                                onSubscribeClick = {
                                    followerViewModel.follow(post.userId)
                                },
                                onLikeClick = { reactionViewModel.addReaction(post.id, selectedReaction ?: Reaction.REACTION_UNSPECIFIED) },
                                onCommentClick = {
                                    selectedPostId = post.id
                                    showComments = true
                                    commentViewModel.loadUsernames()
                                    commentViewModel.loadComments(post.id)
                                },
                                onShareClick = { showShare = true }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Serializable
object FeedScreen

@Preview(showBackground = true)
@Composable
private fun FeedPagePreview() {
    MATheme {
        FeedPage()
    }
}

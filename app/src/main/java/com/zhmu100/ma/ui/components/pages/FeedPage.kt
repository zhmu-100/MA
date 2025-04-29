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
import com.zhmu100.ma.ui.components.buttons.*
import com.zhmu100.ma.ui.components.posts.Comment
import com.zhmu100.ma.ui.components.posts.CommentsBottomSheet
import com.zhmu100.ma.ui.components.posts.PostCard
import com.zhmu100.ma.ui.components.posts.ShareBottomSheet
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    var showComments by remember { mutableStateOf(false) }
    var showShare by remember { mutableStateOf(false) }

    val comments = remember {
        listOf(
            Comment(username = "Мария", text = "Отличная тренировка!", time = "5 мин назад"),
            Comment(username = "Иван", text = "Пост топ 🔥", time = "10 мин назад"),
            Comment(username = "Оля", text = "Где купила коврик?)", time = "15 мин назад"),
            Comment(username = "Антон", text = "Хочу также начать заниматься!", time = "20 мин назад")
        )
    }

    if (showComments) {
        CommentsBottomSheet(
            comments = comments,
            onDismissRequest = { showComments = false }
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
                items(10) { index ->
                    PostCard(
                        username = "Emily123",
                        postDate = "27 апреля 2025 г.",
                        postText = "Сегодня я сходила в зал и съела вкусный завтрак! Фото прикреплены ниже.",
                        isSubscribed = false,
                        onSubscribeClick = { /* TODO */ },
                        onLikeClick = { /* TODO */ },
                        onCommentClick = { showComments = true },
                        onShareClick = { showShare = true }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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

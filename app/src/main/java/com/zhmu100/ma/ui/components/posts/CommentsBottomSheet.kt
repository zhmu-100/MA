package com.zhmu100.ma.ui.components.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Данные одного комментария.
 *
 * @param username Имя пользователя, который оставил комментарий
 * @param text Текст комментария
 * @param time Время публикации комментария
 */

data class Comment(
    val username: String,
    val text: String,
    val time: String
)

/**
 * BottomSheet для отображения списка комментариев с возможностью оставить реакцию и написать новый комментарий.
 *
 * @param comments Список комментариев для отображения
 * @param onDismissRequest Колбэк закрытия BottomSheet
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    comments: List<Comment>,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Комментарии", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var newComment by remember { mutableStateOf("") }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = newComment,
                    onValueChange = { newComment = it },
                    placeholder = { Text("Ваш комментарий") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /* TODO: отправить комментарий */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

/**
 * Компонент отображения одного комментария с возможностью поставить реакцию двойным нажатием.
 *
 * @param comment Данные комментария
 */

@Composable
private fun CommentItem(comment: Comment) {
    var selectedReaction by remember { mutableStateOf<Int?>(null) }
    var showReactions by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val emojiMap = mapOf(
        1 to "👍",
        2 to "❤️",
        3 to "😂",
        4 to "😮",
        5 to "😢",
        6 to "😡"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = comment.username,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = comment.time,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            showReactions = true
                            scope.launch {
                                delay(5000) // Авто-скрытие через 5 сек если пользователь не выберет
                                showReactions = false
                            }
                        }
                    )
                }
        ) {
            Text(
                text = comment.text,
                modifier = Modifier.weight(1f)
            )
            if (selectedReaction != null) {
                Text(
                    text = emojiMap[selectedReaction] ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        if (showReactions) {
            Spacer(modifier = Modifier.height(8.dp))
            ReactionRow(
                selectedReaction = selectedReaction,
                onReactionSelected = {
                    selectedReaction = it
                    showReactions = false
                }
            )
        }
    }
}

/**
 * Компонент горизонтального списка реакций, который появляется при двойном нажатии на комментарий.
 *
 * @param selectedReaction ID выбранной реакции
 * @param onReactionSelected Колбэк при выборе реакции
 */

@Composable
private fun ReactionRow(
    selectedReaction: Int?,
    onReactionSelected: (Int) -> Unit
) {
    val reactions = listOf(
        1 to "👍",
        2 to "❤️",
        3 to "😂",
        4 to "😮",
        5 to "😢",
        6 to "😡"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {
        reactions.forEach { (id, emoji) ->
            Text(
                text = emoji,
                fontSize = 24.sp,
                style = TextStyle(
                    color = if (selectedReaction == id) Color.Unspecified else Color.Gray
                ),
                modifier = Modifier
                    .clickable { onReactionSelected(id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentsBottomSheetPreview() {
    MATheme {
        CommentsBottomSheet(
            comments = listOf(
                Comment(username = "Мария", text = "Отличная тренировка!", time = "5 мин назад"),
                Comment(username = "Иван", text = "Пост топ 🔥", time = "10 мин назад"),
                Comment(username = "Оля", text = "Где купила коврик?)", time = "15 мин назад"),
                Comment(username = "Антон", text = "Хочу также начать заниматься!", time = "20 мин назад")
            ),
            onDismissRequest = {}
        )
    }
}

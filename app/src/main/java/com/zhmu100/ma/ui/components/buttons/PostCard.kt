package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.LightGray

/**
 * Компонент карточки поста для ленты.
 *
 * @param username Имя пользователя, который создал пост.
 * @param postDate Дата публикации поста.
 * @param postText Текст поста.
 * @param isSubscribed Флаг подписки на пользователя (отображает кнопку подписки/отписки).
 * @param onSubscribeClick Обработчик нажатия на кнопку подписки.
 * @param onLikeClick Обработчик нажатия на кнопку лайка.
 * @param onCommentClick Обработчик нажатия на кнопку комментариев.
 * @param onShareClick Обработчик нажатия на кнопку поделиться.
 */

@Composable
fun PostCard(
    username: String,
    postDate: String,
    postText: String,
    isSubscribed: Boolean = false,
    onSubscribeClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    var subscribed by remember { mutableStateOf(isSubscribed) }
    var isLiked by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Avatar, Name, Date, Subscribe button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = postDate,
                        color = Gray,
                        fontSize = 12.sp
                    )
                }

                TextButton(
                    onClick = {
                        subscribed = !subscribed
                        onSubscribeClick()
                    }
                ) {
                    Text(
                        text = if (subscribed) "Вы подписаны" else "Подписаться",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Post Image Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Post Image",
                    modifier = Modifier.size(124.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Post Text
            Text(
                text = postText,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Actions: Like, Comment, Share
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        isLiked = !isLiked
                        onLikeClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.favourite),
                        contentDescription = "Like",
                        tint = if (isLiked) Color.Red else LocalContentColor.current
                    )
                }
                IconButton(onClick = { onCommentClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat_bubble),
                        contentDescription = "Comment"
                    )
                }
                IconButton(onClick = { onShareClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "Share"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    MATheme {
        PostCard(
            username = "Alisa227",
            postDate = "12 марта 2025 г.",
            postText = "Я снова оттягивала этот момент, но больше нельзя. Сегодня день ног, и плевать, что они уже ноют от одной мысли о приседаниях."
        )
    }
}

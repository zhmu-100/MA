package com.zhmu100.ma.ui.components.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.data.Recommendation
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Горизонтальный список карточек с рекомендациями по питанию.
 *
 * Каждая карточка содержит изображение, заголовок и дату. По нажатию на карточку открывается ссылка.
 *
 * @param recommendations Список рекомендаций [Recommendation] для отображения.
 */

@Composable
fun FoodRecommendationCard(
    recommendations: List<Recommendation>
) {
    val uriHandler = LocalUriHandler.current

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recommendations) { recommendation ->
            RecommendationCard(
                recommendation = recommendation,
                onClick = {
                    uriHandler.openUri(recommendation.url)
                }
            )
        }
    }
}

/**
 * Компонент карточки одной рекомендации.
 *
 * Включает фоновое изображение, затемнение, заголовок и дату.
 * Карточка кликабельна — открывает переданный URL.
 *
 * @param recommendation Данные рекомендации [Recommendation].
 * @param onClick Обработчик клика на карточку.
 */

@Composable
private fun RecommendationCard(
    recommendation: Recommendation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.food_recommendation),
                contentDescription = "Recommendation image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = recommendation.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = recommendation.date,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FoodRecommendationCardPreview() {
    MATheme {
        val dummyList = listOf(
            Recommendation(1, "Как вкусно готовить ?", "25 марта 2025", "", "https://example.com/1"),
            Recommendation(2, "Полезные советы", "24 марта 2025", "", "https://example.com/2")
        )
        FoodRecommendationCard(dummyList)
    }
}

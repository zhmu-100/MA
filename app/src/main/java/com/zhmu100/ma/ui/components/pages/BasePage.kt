package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.NavBar
import com.zhmu100.ma.ui.theme.MATheme

/**
 * Базовый компонент страницы с опциональной навигационной панелью.
 *
 * @param hasNavBar Флаг отображения навигационной панели
 * @param modifier Модификатор для настройки внешнего вида контента
 * @param navController Контроллер навигации (опционально)
 * @param navIndex Выбранная иконка в NavBar
 * @param content Композируемая функция контента страницы
 */
@Composable
fun BasePage(
    hasNavBar: Boolean,
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    navIndex: Int = 4,
    content: @Composable (Modifier) -> Unit
) {
    val navPath =
        listOf(FeedScreen, TrainCategoryScreen, FoodScreen, ProfileScreen, ProfileScreen)

    val bottomBar: @Composable (() -> Unit) = if (hasNavBar) {
        {
            NavBar(
                navIndex,
                { navController?.navigate(navPath[it]) },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    } else {
        {}
    }

    Scaffold(
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        content(
            modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasePagePreview() {
    MATheme {
        BasePage(true) {}
    }
}
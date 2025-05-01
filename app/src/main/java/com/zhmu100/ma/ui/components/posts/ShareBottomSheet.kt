package com.zhmu100.ma.ui.components.posts

import androidx.compose.foundation.layout.*
import com.zhmu100.ma.R
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.theme.MATheme

/**
 * BottomSheet для выбора способа поделиться постом.
 *
 * В данный момент реализован только один вариант — "Скопировать ссылку".
 *
 * @param onDismissRequest Колбэк для закрытия BottomSheet
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareBottomSheet(onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Поделиться", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ShareOption(
                    title = "Скопировать ссылку",
                    iconRes = R.drawable.copy
                )
            }
        }
    }
}

/**
 * Элемент выбора варианта поделиться постом.
 *
 * Отображает квадратную кнопку с иконкой и подписью снизу.
 *
 * @param title Текстовое описание действия
 * @param iconRes Ресурс иконки для кнопки
 */

@Composable
fun ShareOption(title: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        SquareIconButton(
            iconResourceId = iconRes,
            onClick = { /* TODO: действие при нажатии */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun ShareBottomSheetPreview() {
    MATheme {
        ShareBottomSheet(
            onDismissRequest = { }
        )
    }
}

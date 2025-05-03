package com.zhmu100.ma.ui.components.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackIconButton
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun HistoryHeader(
    onBackClick: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    currentIndex: Int,
    total: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            BackIconButton(onClick = onBackClick)
            Text(
                text = "История тренировок",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onPrevious,
                enabled = currentIndex > 0
            ) {
                Icon(painter = painterResource(R.drawable.triangle_left), "Previous")
            }
            Text("Тренировка ${currentIndex + 1} из $total")
            IconButton(
                onClick = onNext,
                enabled = currentIndex < total - 1
            ) {
                Icon(painter = painterResource(R.drawable.triangle_right), "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryHeaderPreview() {
    MATheme {
        HistoryHeader({}, {}, {}, 5, 10)
    }
}
package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun HeightSelector(
    modifier: Modifier = Modifier,
    onHeightSelected: (Int) -> Unit = {},
    heightRange: List<Int> = (100..250 step 5).toList().reversed()
) {
    val listState = rememberLazyListState(20)
    val selectedIndex by remember {
        derivedStateOf { // nearest to the center of screen
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val viewportCenter = listState.layoutInfo.viewportSize.height / 2
            val closestItem = visibleItems.minByOrNull {
                val itemCenter = it.offset + it.size / 2
                kotlin.math.abs(itemCenter - viewportCenter)
            }
            closestItem?.index ?: 0
        }
    }
    // Callback when selected index changes
    LaunchedEffect(selectedIndex) {
        onHeightSelected(heightRange[selectedIndex])
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Selected height display
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "${heightRange[selectedIndex]}", fontSize = 64.sp)
            Text(text = "Cm", fontSize = 20.sp, color = LightGray)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(320.dp)
            ) {
                items(heightRange.size) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(60.dp)
                    ) {
                        val isSelected = selectedIndex == index
                        val fontSize = if (isSelected) 40.sp else 32.sp
                        val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        val fontColor = if (isSelected) Black else LightGray
                        // Number text
                        Text(
                            text = "${heightRange[index]}",
                            fontSize = fontSize,
                            fontWeight = fontWeight,
                            textAlign = TextAlign.Center,
                            color = fontColor,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                        HeightLines(isSelected)
                    }
                }
            }
            ArrowLeft()
        }
    }
}

@Composable
private fun ArrowLeft() {
    Icon(
        painter = painterResource(R.drawable.triangle_left),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(64.dp)
    )
}

@Composable
private fun HeightLines(isSelected: Boolean) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .fillMaxHeight()
            .width(60.dp),
    ) {
        repeat(2) {
            HorizontalLine(20.dp, White)
        }
        HorizontalLine(40.dp, if (isSelected) MaterialTheme.colorScheme.primary else White)
        repeat(2) {
            HorizontalLine(20.dp, White)
        }
    }
}

@Composable
private fun HorizontalLine(height: Dp, color: Color) {
    Box(
        modifier = Modifier
            .height(2.dp)
            .width(height)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun HeightSelectorPreview() {
    MATheme {
        HeightSelector()
    }
}
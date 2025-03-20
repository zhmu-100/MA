package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
fun WeightSelector(
    modifier: Modifier = Modifier,
    onWeightSelected: (Int) -> Unit = {},
    weightRange: List<Int> = (0..99).toList()
) {
    val listState = rememberLazyListState(18)
    val selectedIndex by remember { // nearest to the center of screen
        derivedStateOf {
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val viewportCenter = listState.layoutInfo.viewportSize.width / 2
            val closestItem = visibleItems.minByOrNull {
                val itemCenter = it.offset + it.size / 2
                kotlin.math.abs(itemCenter - viewportCenter)
            }
            closestItem?.index ?: 0
        }
    }
    // Callback when selected index changes
    LaunchedEffect(selectedIndex) {
        onWeightSelected(weightRange[selectedIndex])
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Slider
        LazyRow(
            state = listState,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            items(weightRange.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(60.dp)
                ) {
                    val isSelected = selectedIndex == index
                    val fontSize = if (isSelected) 40.sp else 32.sp
                    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    val fontColor = if (isSelected) Black else LightGray
                    // Numbers
                    Text(
                        text = "${weightRange[index]}",
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        textAlign = TextAlign.Center,
                        color = fontColor,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    WeightLines(isSelected)
                }
            }
        }
        ArrowUp()
        // Selected weight display
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "${weightRange[selectedIndex]}", fontSize = 64.sp)
            Text(text = "Kg", fontSize = 20.sp, color = LightGray)
        }
    }
}

@Composable
private fun ArrowUp() {
    Icon(
        painter = painterResource(R.drawable.triangle_up),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(64.dp)
    )
}

@Composable
private fun WeightLines(isSelected: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .fillMaxWidth()
            .height(60.dp),
    ) {
        repeat(2) {
            VerticalLine(20.dp, White)
        }
        VerticalLine(40.dp, if (isSelected) MaterialTheme.colorScheme.primary else White)
        repeat(2) {
            VerticalLine(20.dp, White)
        }
    }
}

@Composable
private fun VerticalLine(height: Dp, color: Color) {
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(height)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun WeightSelectorPreview() {
    MATheme {
        WeightSelector()
    }
}
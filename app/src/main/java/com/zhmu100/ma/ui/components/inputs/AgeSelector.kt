package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun AgeSelector(
    modifier: Modifier = Modifier, onAgeSelected: (Int) -> Unit = {},
    ageRange: List<Int> = (0..99).toList()
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
        onAgeSelected(ageRange[selectedIndex])
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Selected age display
        Text(
            text = "${ageRange[selectedIndex]}",
            fontSize = 64.sp
        )
        ArrowUp()
        // Slider
        LazyRow(
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)
        ) {
            items(ageRange.size) { index ->
                val isSelected = selectedIndex == index
                val fontSize = if (isSelected) 40.sp else 32.sp
                val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                val fontColor = if (isSelected) White else LightGray
                // Numbers
                Text(
                    text = "${ageRange[index]}",
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    textAlign = TextAlign.Center,
                    color = fontColor,
                    modifier = Modifier
                        .width(60.dp)
                )
                if (index < ageRange.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalLine(index == selectedIndex || index == selectedIndex - 1)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
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
private fun VerticalLine(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(if (isSelected) 80.dp else 20.dp)
            .background(if (isSelected) White else LightGray)
    )
}

@Preview(showBackground = true)
@Composable
fun AgeSelectorPreview() {
    MATheme {
        AgeSelector()
    }
}

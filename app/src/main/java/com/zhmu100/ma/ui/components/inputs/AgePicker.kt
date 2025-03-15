package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun AgeSelector(
    modifier: Modifier = Modifier, onAgeSelected: (Int) -> Unit = {}
) {
    val listState = rememberLazyListState(18)
    val ages = (0..99).toList()
    val selectedIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + (listState.layoutInfo.visibleItemsInfo.size / 2)
        }
    }
    // Change number
    LaunchedEffect(selectedIndex) {
        onAgeSelected(ages[selectedIndex])
    }
    // Slider
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "${ages[selectedIndex]}",
            fontSize = 64.sp
        )
        Icon(
            painter = painterResource(R.drawable.triangle_up),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        LazyRow(
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)
        ) {
            items(ages.size) { index ->
                val isSelected = selectedIndex == index
                val fontSize = if (isSelected) 32.sp else 24.sp
                val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                val fontColor = if (isSelected) White else Black
                // Numbers
                Text(
                    text = "${ages[index]}",
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    textAlign = TextAlign.Center,
                    color = fontColor,
                    modifier = Modifier
                        .width(40.dp)
                        .padding(vertical = 8.dp)
                )
                // Vertical lines
                if (index < ages.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(if (index == selectedIndex || index == selectedIndex - 1) 40.dp else 20.dp)
                            .background(if (index == selectedIndex || index == selectedIndex - 1) White else Black)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgeSelectorPreview() {
    MATheme {
        AgeSelector()
    }
}

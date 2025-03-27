package com.zhmu100.ma.ui.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun NumberSelector(
    range: List<Int>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(selectedItem - 2)
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
        onItemSelected(range[selectedIndex])
    }

    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(60.dp)
            .height(200.dp)

    ) {
        items(range.size) { index ->
            val isSelected = selectedIndex == index
            Text(
                text = index.toString(),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { onItemSelected(range[index]) }
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NumberSelectorPreview() {
    MATheme {
        var text by remember { mutableStateOf("Test") }
        Column {
            Text(text)
            NumberSelector((0..23).toList(), 8, onItemSelected = { text = it.toString() })
        }
    }
}
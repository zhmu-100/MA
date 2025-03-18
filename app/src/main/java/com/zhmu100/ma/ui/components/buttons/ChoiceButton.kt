package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun ChoiceButton(
    options: List<String>,
    modifier: Modifier = Modifier,
    onOptionChoose: (id: Int) -> Unit = {}
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    Column(modifier = modifier) {
        options.forEachIndexed { index, option ->
            Button(
                onClick = {
                    onOptionChoose(index)
                    selectedIndex = index
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Black
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(2.dp, CircleShape)
                    .background(White)
            ) {
                val circleColor =
                    if (selectedIndex == index) MaterialTheme.colorScheme.primary else White
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = option, modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Black), shape = CircleShape)
                            .background(color = circleColor, shape = CircleShape)
                            .size(30.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChoiceButtonPreview() {
    MATheme {
        ChoiceButton(
            listOf("Похудеть", "Набрать вес", "Другое")
        )
    }
}
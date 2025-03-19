package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun ChoiceButtonFill(
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
                colors = if (selectedIndex == index) ButtonDefaults.buttonColors()
                else ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(25))
                    .background(
                        if (selectedIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            White
                    )
            ) {
                Text(text = option, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChoiceButtonFillPreview() {
    MATheme {
        ChoiceButtonFill(
            listOf("Похудеть", "Набрать вес", "Другое")
        )
    }
}
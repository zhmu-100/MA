package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.components.inputs.BorderlessInputLine
import com.zhmu100.ma.ui.data.TrainRowData
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun TrainGymPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    var workoutName by remember { mutableStateOf("") }
    val trainRows = remember { mutableStateListOf<TrainRowData>() }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            BorderlessInputLine(placeholder = "Тренировка", onTextChanged = { workoutName = it })
            LazyColumn(modifier = Modifier.weight(1f), state = LazyListState()) {
                items(trainRows.count()) { index ->
                    TrainRow(
                        data = trainRows[index],
                        onExerciseChange = { newValue ->
                            trainRows[index] = trainRows[index].copy(exercise = newValue)
                        },
                        onValueChange = { newValue ->
                            trainRows[index] = trainRows[index].copy(value = newValue)
                        },
                        onDelete = { trainRows.removeAt(index) }
                    )
                }
            }
            ThemedIconButton(
                Icons.Default.Add,
                onClick = { trainRows.add(TrainRowData("", "")) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Stats(modifier = Modifier.padding(bottom = 8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Пауза")
                }
                Button(
                    onClick = { navController?.navigate(TrainMoodScreen) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Завершить")
                }
            }
        }
    }
}

@Composable
private fun TrainRow(
    data: TrainRowData,
    onExerciseChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        SquareInput(
            value = data.exercise,
            onTextChanged = onExerciseChange,
            placeholder = "Упражнение",
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        SquareInput(
            value = data.value,
            onTextChanged = onValueChange,
            placeholder = "0",
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 8.dp)
        )
        SquareIconButton(
            Icons.Default.Delete,
            onClick = onDelete
        )
    }
}

@Composable
private fun SquareInput(
    value: String,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    placeholder: String = ""
) {
    TextField(
        value = value,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = {
            Text(
                placeholder,
                textAlign = TextAlign.Center,
                color = LightGray,
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier.shadow(1.dp, shape = RoundedCornerShape(15))
    )
}

@Composable
private fun Stats(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text("0:40:30", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Длительность")
    }
}

@Serializable
object TrainGymScreen

@Preview(showBackground = true)
@Composable
private fun TrainGymPagePreview() {
    MATheme {
        TrainGymPage()
    }
}
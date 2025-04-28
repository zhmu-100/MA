package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseReaction
import com.zhmu100.ma.domain.viewModel.TrainingGymViewModel
import com.zhmu100.ma.domain.viewModel.TrainingViewModel
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.components.inputs.BorderlessInputLine
import com.zhmu100.ma.ui.data.TrainRowData
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.Duration
import kotlin.math.roundToInt

@Composable
fun TrainGymPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    gymViewModel: TrainingGymViewModel = koinViewModel(),
    trainViewModel: TrainingViewModel = koinViewModel()
) {
    var workoutName by remember { mutableStateOf("") }

    // Обновление таймера каждую секунду
    LaunchedEffect(trainViewModel.isTrainingStarted.value, trainViewModel.isPaused.value) {
        if (trainViewModel.isTrainingStarted.value && !trainViewModel.isPaused.value) {
            while (true) {
                trainViewModel.updateTimer()
                delay(1000)
            }
        }
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier.fillMaxWidth()
        ) {
            BorderlessInputLine(
                placeholder = "Тренировка",
                onTextChanged = { workoutName = it })
            if (!trainViewModel.isTrainingStarted.value) {
                Button(
                    onClick = { trainViewModel.startTraining() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Начать тренировку")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), state = LazyListState()) {
                    items(gymViewModel.trainRows.count()) { index ->
                        TrainRow(
                            data = gymViewModel.trainRows[index],
                            onExerciseChange = { exercise ->
                                gymViewModel.updateExercise(index, exercise)
                            },
                            onValueChange = { value ->
                                gymViewModel.updateValue(index, value)
                            },
                            onDelete = { gymViewModel.removeExercise(index) },
                            viewModel = gymViewModel
                        )
                    }
                }
                ThemedIconButton(
                    Icons.Default.Add,
                    onClick = { gymViewModel.addExerciseRow() },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Stats(
                    trainViewModel.totalExerciseTime.value,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { trainViewModel.togglePause() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(25)
                    ) {
                        Text(if (trainViewModel.isPaused.value) "Продолжить" else "Пауза")
                    }
                    Button(
                        onClick = {
                            val workout =
                                gymViewModel.getWorkout(trainViewModel.totalExerciseTime.value)
                            workout?.let {
                                trainViewModel.endTraining(it)
                                trainViewModel.saveWorkout(it, ExerciseReaction.EXCELLENT, "None")
                            }
                            navController?.navigate(TrainMoodScreen)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        shape = RoundedCornerShape(25)
                    ) {
                        Text("Завершить")
                    }
                }
            }
        }
    }
}

@Composable
private fun TrainRow(
    data: TrainRowData,
    onExerciseChange: (ExerciseName) -> Unit,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
    viewModel: TrainingGymViewModel,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        ExerciseDropdown(
            selectedExercise = data.exercise,
            onExerciseSelected = onExerciseChange,
            viewModel = viewModel,
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
private fun ExerciseDropdown(
    selectedExercise: ExerciseName,
    onExerciseSelected: (ExerciseName) -> Unit,
    viewModel: TrainingGymViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = if (selectedExercise == ExerciseName.UNSPECIFIED) "Выберите упражнение"
            else selectedExercise.displayName,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.exerciseOptions.forEach { exercise ->
                DropdownMenuItem(
                    text = { Text(exercise.displayName) },
                    onClick = {
                        onExerciseSelected(exercise)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)
                )
            }
        }
    }
}

@Composable
private fun Stats(
    duration: Duration,
    modifier: Modifier = Modifier
) {
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    val durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(durationString, fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
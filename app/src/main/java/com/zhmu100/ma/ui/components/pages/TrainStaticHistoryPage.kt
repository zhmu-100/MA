package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.utils.DurationUtils
import com.zhmu100.ma.domain.viewModel.TrainingHistoryViewModel
import com.zhmu100.ma.ui.components.train.EmptyHistoryState
import com.zhmu100.ma.ui.components.train.HistoryHeader
import com.zhmu100.ma.ui.components.train.WorkoutFeedback
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.Duration

@Composable
fun TrainStaticHistoryPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    trainingHistoryViewModel: TrainingHistoryViewModel = koinViewModel()
) {
    val workouts by trainingHistoryViewModel.filteredWorkouts.collectAsState()
    var currentWorkoutIndex by rememberSaveable { mutableIntStateOf(-1) }

    // Загрузка статичных тренировок
    LaunchedEffect(Unit) {
        trainingHistoryViewModel.loadWorkouts()
        trainingHistoryViewModel.filterWorkoutsByExerciseType(ExerciseType.EXERCISE_TYPE_STATIC)
        currentWorkoutIndex = 0
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(modifier = baseModifier.fillMaxSize()) {
            HistoryHeader(
                onBackClick = { navController?.popBackStack() },
                onPrevious = {
                    if (currentWorkoutIndex > 0) currentWorkoutIndex--
                },
                onNext = {
                    if (currentWorkoutIndex < workouts.size - 1) currentWorkoutIndex++
                },
                currentIndex = currentWorkoutIndex,
                total = workouts.size
            )

            if (workouts.isEmpty()) {
                EmptyHistoryState()
                return@Column
            }

            val workout = workouts[currentWorkoutIndex]

            // Имя тренировки
            WorkoutName(workout.name)

            // Статистика по упражнениям
            if (workout.excercises.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(workout.excercises) { exercise ->
                        ExerciseItem(exercise = exercise)
                    }
                }

                // Общее время
                WorkoutDuration(DurationUtils.isoStringToDuration(workout.excercises[0].duration))

                // Эмоция и заметка
                WorkoutFeedback(workout.excercises[0])
            }
        }
    }
}

@Composable
private fun WorkoutName(name: String) {
    Text(
        text = name,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun ExerciseItem(exercise: Exercise) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = exercise.name.displayName, fontSize = 18.sp)
        Text(text = (exercise.reps ?: 0) .toString() + " повторений", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun WorkoutDuration(duration: Duration?) {
    val durationString = when {
        duration == null -> "—"
        duration.isZero -> "00:00:00"
        else -> {
            val totalSeconds = duration.seconds
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = durationString, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Длительность")
        }
    }
}

@Serializable
object TrainStaticHistoryScreen
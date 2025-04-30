package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.statistic.GPSPosition
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.domain.model.training.WorkoutStats
import com.zhmu100.ma.domain.viewModel.TrainingHistoryViewModel
import com.zhmu100.ma.ui.components.buttons.BackIconButton
import com.zhmu100.ma.ui.components.inputs.MultiLineTextField
import com.zhmu100.ma.ui.data.MoodOption
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainDynamicHistoryPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    trainingHistoryViewModel: TrainingHistoryViewModel = koinViewModel()
) {
    val workouts by trainingHistoryViewModel.filteredWorkouts.collectAsState()
    var currentWorkoutIndex by rememberSaveable { mutableIntStateOf(-1) }

    // Filter for dynamic workouts when page loads
    LaunchedEffect(Unit) {
        trainingHistoryViewModel.loadWorkouts()
        trainingHistoryViewModel.filterWorkoutsByExerciseType(ExerciseType.DYNAMIC)
        currentWorkoutIndex = 0
    }

    LaunchedEffect(currentWorkoutIndex) {
        val workout = workouts.getOrNull(currentWorkoutIndex) ?: return@LaunchedEffect
        // Assume workout has an exerciseId field
        if (workout.exercises.isNotEmpty()) {
            workout.exercises[0].id?.let {
                trainingHistoryViewModel.loadGPSDataForWorkout(
                    it,
                    workout.exercises[0]
                )
            }
        }
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(modifier = baseModifier.fillMaxSize()) {
            // Header with navigation
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

            // Empty state
            if (workouts.isEmpty()) {
                EmptyHistoryState()
                return@Column
            }

            val workout = workouts[currentWorkoutIndex]
            val gpsPoints by trainingHistoryViewModel.currentGPSData.collectAsState()
            val stats by trainingHistoryViewModel.currentWorkoutStats.collectAsState()

            // Map display
            WorkoutMap(
                gpsPoints = gpsPoints,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // Workout stats
            stats?.let {
                WorkoutStats(stats = it)
            }

            // Mood and notes
            if (workout.exercises.isNotEmpty()) {
                WorkoutFeedback(workout.exercises[0])
            }
        }
    }
}

@Composable
private fun HistoryHeader(
    onBackClick: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    currentIndex: Int,
    total: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            BackIconButton(onClick = onBackClick)
            Text(
                text = "История тренировок",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onPrevious,
                enabled = currentIndex > 0
            ) {
                Icon(painter = painterResource(R.drawable.triangle_left), "Previous")
            }
            Text("Тренировка ${currentIndex + 1} из ${total}")
            IconButton(
                onClick = onNext,
                enabled = currentIndex < total - 1
            ) {
                Icon(painter = painterResource(R.drawable.triangle_right), "Next")
            }
        }
    }
}

@Composable
private fun EmptyHistoryState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Нет завершенных пробежек", style = MaterialTheme.typography.headlineMedium)
        Text("Ваши тренировки будут отображаться здесь", textAlign = TextAlign.Center)
    }
}

@Composable
private fun WorkoutMap(gpsPoints: List<GPSPosition>, modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState()

    // Convert GPSPosition to LatLng
    val latLngPoints = remember(gpsPoints) {
        gpsPoints.map { LatLng(it.latitude, it.longitude) }
    }

    // Center camera on first point
    LaunchedEffect(latLngPoints) {
        if (latLngPoints.isNotEmpty()) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLngPoints[0], 15f)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false)
    ) {
        // Route line
        if (latLngPoints.size >= 2) {
            Polyline(
                points = latLngPoints,
                color = MaterialTheme.colorScheme.primary,
                width = 8f
            )
        }

        // Start/End markers
        latLngPoints.firstOrNull()?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Start",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }
        latLngPoints.lastOrNull()?.let {
            Marker(
                state = MarkerState(position = it),
                title = "End",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }
    }
}

@Composable
private fun WorkoutStats(stats: WorkoutStats) {
    val duration = stats.duration
    val durationString = String.format(
        "%02d:%02d:%02d",
        duration.toHours(),
        duration.toMinutes() % 60,
        duration.seconds % 60
    )

    val pace = if (stats.totalDistance > 0) {
        (duration.seconds / 60.0) / (stats.totalDistance / 1000)
    } else 0.0
    val paceMinutes = pace.toInt()
    val paceSeconds = ((pace - paceMinutes) * 60).toInt()
    val paceString = String.format("%02d:%02d", paceMinutes, paceSeconds)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(value = "%.2f".format(stats.totalDistance / 1000), label = "км")
        StatItem(value = durationString, label = "длительность")
        StatItem(value = paceString, label = "мин/км")
        StatItem(value = "${stats.calories}", label = "ккал")
        StatItem(value = "${stats.steps}", label = "шаги")
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = label)
    }
}

@Composable
private fun WorkoutFeedback(exercise: Exercise) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        // Mood display
        exercise.reaction?.let {
            val mood = MoodOption.getByReaction(it)
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Icon(
                    painter = painterResource(id = mood.iconId),
                    contentDescription = mood.description,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Оценка: ${mood.description}",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Notes
        exercise.note?.let {
            MultiLineTextField(
                text = it,
                placeholder = "Заметки о тренировке",
                onTextChanged = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                readOnly = true
            )
        }
    }
}

@Serializable
object TrainDynamicHistoryScreen
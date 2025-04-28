package com.zhmu100.ma.ui.components.pages

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.zhmu100.ma.domain.model.training.ExerciseReaction
import com.zhmu100.ma.domain.viewModel.TrainingMapViewModel
import com.zhmu100.ma.domain.viewModel.TrainingViewModel
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.Duration
import kotlin.math.roundToInt

const val DEFAULT_UPDATE_INTERVAL = 5000L // ms

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrainMapPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    trainViewModel: TrainingViewModel = koinViewModel(),
    mapViewModel: TrainingMapViewModel = koinViewModel()
) {
    // handle permissions
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                locationPermissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (locationPermissionsState.allPermissionsGranted) {
        MainContent(
            navController = navController,
            modifier = modifier,
            trainViewModel = trainViewModel,
            mapViewModel = mapViewModel
        )
    } else {
        AccessPermissions(modifier, locationPermissionsState)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AccessPermissions(
    modifier: Modifier = Modifier,
    locationPermissionsState: MultiplePermissionsState
) {
    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            modifier = baseModifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Для отображения карты необходимы разрешения на доступ к местоположению")
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text("Запросить разрешения")
            }
        }
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    trainViewModel: TrainingViewModel = koinViewModel(),
    mapViewModel: TrainingMapViewModel = koinViewModel()
) {
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
            Header(trainViewModel.isTrainingStarted.value) { navController?.popBackStack() }
            if (!trainViewModel.isTrainingStarted.value) {
                Button(
                    onClick = { trainViewModel.startTraining() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Начать тренировку")
                }
            } else {
                MapWithLocation(
                    route = mapViewModel.routePoints,
                    isPaused = trainViewModel.isPaused.value,
                    onUserLocationFound = { location ->
                        mapViewModel.addNewPoint(location, trainViewModel.totalExerciseTime.value)
                    },
                )
                StatsByRoute(
                    distance = mapViewModel.totalDistance.value,
                    duration = trainViewModel.totalExerciseTime.value,
                    steps = mapViewModel.stepsCount.value,
                    calories = mapViewModel.caloriesBurned.value
                )
                ActionButtons(
                    isPaused = trainViewModel.isPaused.value,
                    onPause = { trainViewModel.togglePause() },
                    onEnd = {
                        val workout =
                            mapViewModel.getWorkout(trainViewModel.totalExerciseTime.value)
                        workout?.let {
                            trainViewModel.endTraining(it)
                            trainViewModel.saveWorkout(it, ExerciseReaction.EXCELLENT, "None")
                        }
                        navController?.navigate(TrainMoodScreen)
                    },
                )
            }
        }
    }
}

@Composable
private fun Header(isTrainingStarted: Boolean, onBackClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        if (!isTrainingStarted) {
            IconButton(
                onClick = onBackClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }
        }
        Text(
            text = "Тренировка",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun StatsByRoute(
    distance: Double,
    duration: Duration,
    steps: Int,
    calories: Int
) {
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    val durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    val pace = if (distance > 0) (duration.seconds / 60.0) / (distance / 1000) else 0.0
    val paceMinutes = pace.toInt()
    val paceSeconds = ((pace - paceMinutes) * 60).roundToInt()
    val paceString = String.format("%02d:%02d", paceMinutes, paceSeconds)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Stat(steps.toString(), "Шаги")
            Stat("%.2f".format(distance / 1000), "км")
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Stat(durationString, "Длительность")
            Stat(calories.toString(), "ккал")
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Stat("120", "уд/мин")
            Stat(paceString, "мин/км")
        }
    }
}

@Composable
private fun ActionButtons(
    isPaused: Boolean,
    onPause: () -> Unit,
    onEnd: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onPause,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(25)
        ) {
            Text(if (isPaused) "Продолжить" else "Пауза")
        }
        Button(
            onClick = onEnd,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(25)
        ) {
            Text("Завершить")
        }
    }
}

@Composable
private fun MapWithLocation(
    modifier: Modifier = Modifier,
    route: SnapshotStateList<LatLng>,
    isPaused: Boolean,
    onUserLocationFound: (LatLng) -> Unit = {},
) {
    val context = LocalContext.current
    val fusedLocationClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        try {
            val locationResult = fusedLocationClient.lastLocation
            locationResult.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    userLocation?.let { latLng ->
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(latLng, 15f)
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.i("Map", "error")
        }
    }

    // Update location periodically when not paused
    LaunchedEffect(isPaused) {
        if (!isPaused) {
            delay(5000)
            while (true) {
                try {
                    val locationResult = fusedLocationClient.lastLocation
                    locationResult.addOnSuccessListener { location ->
                        location?.let {
                            val newLocation = LatLng(it.latitude, it.longitude)
                            userLocation = newLocation
                            onUserLocationFound(newLocation)
                        }
                    }
                } catch (e: SecurityException) {
                    Log.i("Map", "error")
                }
                delay(DEFAULT_UPDATE_INTERVAL)
            }
        }
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        userLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Ваше местоположение"
            )
        }
        // Рисуем линию маршрута
        Polyline(
            points = route.toList(),
            color = MaterialTheme.colorScheme.primary,
            width = 10f
        )
    }
}

@Composable
private fun Stat(statValue: String, statName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(statValue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(statName)
    }
}


@Serializable
object TrainMapScreen

@Preview(showBackground = true)
@Composable
private fun TrainMapPagePreview() {
    MATheme {
        MainContent()
    }
}
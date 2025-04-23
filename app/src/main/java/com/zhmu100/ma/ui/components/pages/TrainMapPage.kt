package com.zhmu100.ma.ui.components.pages

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.google.maps.android.ktx.utils.sphericalDistance
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.Instant
import kotlin.math.roundToInt
import kotlin.random.Random

const val DEFAULT_DISTANCE_THRESHOLD = 20.0 // meters
const val DEFAULT_UPDATE_INTERVAL = 5000L // ms
const val AVERAGE_STEP_LENGTH = 0.762 // meters
const val CALORIES_PER_KM = 60
const val CALORIES_SPEED_FACTOR = 1 / 12

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrainMapPage(modifier: Modifier = Modifier, navController: NavController? = null) {
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
        MainContent(navController = navController, modifier = modifier)
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
private fun MainContent(modifier: Modifier = Modifier, navController: NavController? = null) {
    val routePoints = remember { mutableStateListOf<LatLng>() }

    var isPaused by remember { mutableStateOf(false) }
    var isRunning by remember { mutableStateOf(true) }
    var startTime by remember { mutableStateOf<Instant?>(null) }
    var pausedTime by remember { mutableStateOf<Instant?>(null) }
    var totalPausedDuration by remember { mutableStateOf(Duration.ZERO) }
    var totalRunningTime by remember { mutableStateOf(Duration.ZERO) }

    var totalDistance by remember { mutableStateOf(0.0) }
    var stepsCount by remember { mutableStateOf(0) }
    var caloriesBurned by remember { mutableStateOf(0) }

    // Update timer every second
    LaunchedEffect(isRunning, isPaused) {
        if (isRunning && !isPaused) {
            startTime = startTime ?: Instant.now()
            while (true) {
                val now = Instant.now()
                val pausedDuration = if (pausedTime != null) {
                    Duration.between(pausedTime, now)
                } else {
                    Duration.ZERO
                }
                totalRunningTime =
                    Duration.between(startTime, now).minus(totalPausedDuration).plus(pausedDuration)
                delay(1000)
            }
        }
    }

    // Function to update all stats
    fun updateStats() {
        totalDistance = calculateTotalDistance(routePoints)
        stepsCount = calculateSteps(totalDistance)
        caloriesBurned = calculateCalories(totalDistance, totalRunningTime)
    }

    fun togglePause() {
        if (isPaused) {
            totalPausedDuration = totalPausedDuration.plus(
                Duration.between(pausedTime, Instant.now())
            )
            pausedTime = null
        } else {
            pausedTime = Instant.now()
        }
        isPaused = !isPaused
    }

    fun addNewPointIfNeeded(newPoint: LatLng) {
        if (routePoints.isEmpty()) {
            routePoints.add(newPoint)
            return
        }

        val lastPoint = routePoints.last()
        val distance = distance(lastPoint, newPoint)
        if (distance >= DEFAULT_DISTANCE_THRESHOLD) {
            routePoints.add(newPoint)
            updateStats()
        }
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            Header()
            MapWithLocation(
                route = routePoints,
                isPaused = isPaused,
                onUserLocationFound = { location ->
                    addNewPointIfNeeded(location)
                },
            )
            StatsByRoute(
                distance = totalDistance,
                duration = totalRunningTime,
                steps = stepsCount,
                calories = caloriesBurned
            )
            ActionButtons(
                isPaused = isPaused,
                onPause = { togglePause() },
                onEnd = {
                    isRunning = false
                    navController?.navigate(TrainMoodScreen)
                },
            )
        }
    }
}

@Composable
private fun Header() {
    Text(
        "Тренировка",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
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

    val pace = if (distance > 0) (duration.toSeconds() / 60.0) / (distance / 1000) else 0.0
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
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        try {
            val locationResult = fusedLocationClient.lastLocation
            locationResult.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    userLocation?.let { latLng ->
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
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

private fun newRandomPoint(lastPoint: LatLng): LatLng {
    val latOffset = (Random.nextDouble() - 0.5) * 0.01
    val lngOffset = (Random.nextDouble() - 0.5) * 0.01

    val newPoint = LatLng(
        lastPoint.latitude + latOffset,
        lastPoint.longitude + lngOffset
    )
    return newPoint
}

/**
 * Calculates distance between two points in meters
 */
private fun distance(point1: LatLng, point2: LatLng): Double {
    return point1.sphericalDistance(point2)
}

/**
 * Calculates total distance of the route in meters
 */
private fun calculateTotalDistance(points: List<LatLng>): Double {
    if (points.size < 2) return 0.0

    var total = 0.0
    for (i in 0 until points.size - 1) {
        total += distance(points[i], points[i + 1])
    }
    return total
}

/**
 * Calculates steps count based on distance (approximate)
 */
private fun calculateSteps(distance: Double): Int {
    return (distance / AVERAGE_STEP_LENGTH).toInt()
}

/**
 * Calculates burned calories based on distance and time
 */
private fun calculateCalories(distance: Double, duration: Duration): Int {
    val km = distance / 1000
    val hours = duration.toHours().toDouble() + duration.toMinutes().toDouble() / 60
    val speed = if (hours > 0) km / hours else 0.0

    // Base calories + intensity factor
    return (km * CALORIES_PER_KM * (1 + speed * CALORIES_SPEED_FACTOR)).toInt()
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
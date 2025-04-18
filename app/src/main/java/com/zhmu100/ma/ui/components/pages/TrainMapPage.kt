package com.zhmu100.ma.ui.components.pages

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
    var state by remember { mutableStateOf(true) }

    // Пример точек маршрута (замените на свои)
    var routePoints by remember {
        mutableStateOf(
            listOf(
                LatLng(55.751244, 37.618423),  // Москва, Красная площадь
                LatLng(55.753930, 37.620795),  // ГУМ
                LatLng(55.755814, 37.617635)   // Исторический музей
            )
        )
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            Text(
                "Тренировка",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (state) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(Black)
                )
            } else {
                MapWithLocation(route = routePoints)
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("5000", "Шаги")
                    Stat("4:12", "км")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("0:40:00", "Длительность")
                    Stat("300", "ккал")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Stat("120", "уд/мин")
                    Stat("9:14", "мин/км")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { state = !state },
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

                Button(
                    onClick = { routePoints = addRandomPoint(routePoints) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Новая точка")
                }
            }
        }
    }
}

@Composable
private fun MapWithLocation(modifier: Modifier = Modifier, route: List<LatLng>) {
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

        // Отображаем точки маршрута
        route.forEachIndexed { index, point ->
            Marker(
                state = MarkerState(position = point),
                title = "Точка ${index + 1}",
                snippet = "Широта: ${point.latitude}, Долгота: ${point.longitude}"
            )
        }

        // Рисуем линию маршрута
        Polyline(
            points = route,
            color = MaterialTheme.colorScheme.primary,
            width = 5f
        )
    }
}

@Composable
private fun Stat(statName: String, statValue: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(statName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(statValue)
    }
}

private fun addRandomPoint(list: List<LatLng>): List<LatLng> {
    val latOffset = (Random.nextDouble() - 0.5) * 0.01
    val lngOffset = (Random.nextDouble() - 0.5) * 0.01

    val newPoint = LatLng(
        list[list.size - 1].latitude + latOffset,
        list[list.size - 1].longitude + lngOffset
    )
    return list + newPoint;
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
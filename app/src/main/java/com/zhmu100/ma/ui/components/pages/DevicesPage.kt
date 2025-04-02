package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.CategoryButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.theme.LightGreen
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.Orange
import kotlinx.serialization.Serializable

@Composable
fun DevicesPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            var devicesInd by remember { mutableIntStateOf(0) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Устройства",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                CategoryButton(
                    "Все",
                    devicesInd == 0,
                    onClick = { devicesInd = 0 },
                    modifier = Modifier.padding(end = 8.dp)
                )
                CategoryButton(
                    "Часы",
                    devicesInd == 1,
                    onClick = { devicesInd = 1 },
                    modifier = Modifier.padding(end = 8.dp)
                )
                CategoryButton(
                    "Весы",
                    devicesInd == 2,
                    onClick = { devicesInd = 2 },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            DeviceCard("Apple Watch", true, DeviceType.WATCH)
            ThemedIconButton(Icons.Default.Add, onClick = { navController?.navigate(DeviceScreen) })
        }
    }
}

@Composable
private fun DeviceCard(text: String, isActive: Boolean, type: DeviceType) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .shadow(2.dp, shape = RoundedCornerShape(25))
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SquareIconButton(
                R.drawable.watch,
                background = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .size(64.dp)
            )
            Column {
                Text(text)
                if (isActive) {
                    Text("Подключено", color = LightGreen)
                } else {
                    Text("Отключено", color = Orange)
                }
            }
            SquareIconButton(Icons.Default.Delete, modifier = Modifier.padding(8.dp))
        }
    }
}

private enum class DeviceType {
    WATCH
}

@Serializable
object DevicesScreen

@Preview(showBackground = true)
@Composable
private fun DevicesPagePreview() {
    MATheme {
        DevicesPage()
    }
}
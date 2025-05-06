package com.zhmu100.ma.ui.components.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.zhmu100.ma.R
import com.zhmu100.ma.domain.model.profile.UserProfile
import com.zhmu100.ma.domain.utils.rememberImagePicker
import com.zhmu100.ma.domain.viewModel.NotificationViewModel
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
import com.zhmu100.ma.domain.viewModel.ViewState
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.BigIconButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.theme.LightGray
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()
    val profilePhotoUrl by viewModel.profilePhotoUrl.collectAsState()

    val openGallery = rememberImagePicker { bytes, fileName, mimeType ->
        viewModel.updateProfilePhoto(bytes, fileName, mimeType)
    }

    val profile: UserProfile? = (profileState as? ViewState.Success)?.data
    val followersCount = profile?.followerCount
    val followingCount = profile?.followingCount

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    BasePage(
        true,
        navIndex = 4,
        modifier = modifier,
        navController = navController
    ) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Header(navController)
            ProfileImage(url = profilePhotoUrl ?: "", onEditClick = openGallery)
            Text(profile?.name ?: "", fontSize = 20.sp)
            Text("online", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            SocialStats(
                followersCount = followersCount ?: 0,
                followingCount = followingCount ?: 0,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            MiddleButtons(navController)
            Text(
                "Мои записи",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            RoundButton("Создать запись", onClick = { navController?.navigate(PostScreen) })
        }
    }
}

@Composable
private fun MiddleButtons(navController: NavController?, notificationViewModel: NotificationViewModel = koinViewModel()) {

    val triggeredNotificationIds by notificationViewModel.triggeredNotificationIds.collectAsState()

    val activeNotifications = triggeredNotificationIds.isNotEmpty()

    BigIconButton(
        text = "Мои параметры",
        drawableResId = R.drawable.ruler,
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = { navController?.navigate(ProfileParametersScreen) }
    )
    Box(modifier = Modifier.padding(bottom = 8.dp)) {
        BigIconButton(
            text = "Напоминания",
            drawableResId = R.drawable.alarm,
            modifier = Modifier,
            onClick = { navController?.navigate(RemindersScreen) }
        )

        if (activeNotifications) {
            Log.d(null, "notificaitons")
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.Red, shape = CircleShape)
                    .align(Alignment.TopStart)
            )
        }
    }
    BigIconButton(
        text = "Мои устройства",
        drawableResId = R.drawable.devices,
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = { navController?.navigate(DevicesScreen) }
    )
    BigIconButton(
        text = "Статистика",
        drawableResId = R.drawable.bars,
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = { navController?.navigate(StatisticsScreen) }
    )
}

@Composable
private fun Header(navController: NavController?) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        BackButton(
            text = "•••",
            isIconActive = false,
            onClick = { navController?.navigate(SettingsScreen) })
    }
}

@Composable
private fun ProfileImage(url: String, onEditClick: () -> Unit) {
    Box(modifier = Modifier.padding(bottom = 8.dp)) {
        AsyncImage(
            model = url,
            contentDescription = "Profile picture",
            placeholder = painterResource(R.drawable.person),
            error = painterResource(R.drawable.person),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(LightGray)
        )
        ThemedIconButton(
            imageVector = Icons.Default.Create,
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onEditClick
        )
    }
}

@Composable
private fun SocialStats(
    followersCount: Int,
    followingCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        StatItem(
            count = followersCount,
            label = "Подписчики",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            count = followingCount,
            label = "Подписки",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = count.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Serializable
object ProfileScreen

@Preview(showBackground = true)
@Composable
private fun ProfilePagePreview() {
    MATheme {
        ProfilePage()
    }
}
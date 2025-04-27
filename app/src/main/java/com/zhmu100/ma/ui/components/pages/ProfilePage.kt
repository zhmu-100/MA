package com.zhmu100.ma.ui.components.pages

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
private fun MiddleButtons(navController: NavController?) {
    BigIconButton(
        text = "Мои параметры",
        drawableResId = R.drawable.ruler,
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = { navController?.navigate(ProfileParametersScreen) }
    )
    BigIconButton(
        text = "Напоминания",
        drawableResId = R.drawable.alarm,
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = { navController?.navigate(RemindersScreen) }
    )
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

@Serializable
object ProfileScreen

@Preview(showBackground = true)
@Composable
private fun ProfilePagePreview() {
    MATheme {
        ProfilePage()
    }
}
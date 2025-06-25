package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.model.profile.Birthdate
import com.zhmu100.ma.domain.model.profile.UserProfile
import com.zhmu100.ma.domain.utils.calculateAge
import com.zhmu100.ma.domain.utils.calculateYear
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
import com.zhmu100.ma.domain.viewModel.ViewState
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.DoubleButton
import com.zhmu100.ma.ui.components.buttons.RoundButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.math.round

@Composable
fun ProfileParametersPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()

    val profile: UserProfile? = (profileState as? ViewState.Success)?.data
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var height by remember { mutableFloatStateOf(profile?.height?.toFloat() ?: 170f) }
    var weight by remember { mutableFloatStateOf(profile?.weight?.toFloat() ?: 70f) }
    var age by remember {
        mutableFloatStateOf(
            calculateAge(profile?.birthdate?.year ?: 2000).toFloat()
        )
    }
    var gender by remember { mutableStateOf(profile?.bio ?: "M") }

    BasePage(false, modifier = modifier) { baseModifier ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = baseModifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                BackButton(
                    text = "Назад",
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { navController?.navigate(ProfileScreen) })
                Text(
                    "Изменить параметры",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            InputLine(label = "Имя", initValue = name, onTextChanged = { name = it })
            Text("Рост ${round(height)} см")
            Slider(
                value = height,
                onValueChange = { height = it },
                valueRange = 100f..250f,
                steps = 149
            )
            Text("Вес ${round(weight)} кг")
            Slider(
                value = weight,
                onValueChange = { weight = it },
                valueRange = 50f..150f,
                steps = 99
            )
            Text("Возраст ${round(age)} лет")
            Slider(
                value = age,
                onValueChange = { age = it },
                valueRange = 0f..100f,
                steps = 99
            )
            Text("Пол $gender")
            DoubleButton(
                leftText = "Мужской",
                rightText = "Женский",
                onLeftClick = { gender = "M" },
                onRightClick = { gender = "F" },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            RoundButton(
                text = "Сохранить",
                onClick = {
                    val updatedProfile = profile?.copy(
                        name = name,
                        height = height.toDouble(),
                        weight = weight.toDouble(),
                        birthdate = Birthdate(
                            year = calculateYear(age.toInt()),
                            month = 1,
                            day = 1
                        ),
                        bio = gender
                    )

                    updatedProfile?.let {
                        viewModel.updateProfile(it)
                        navController?.navigateUp()
                    }
                }
            )
        }
    }
}


@Serializable
object ProfileParametersScreen

@Preview(showBackground = true)
@Composable
private fun ProfileParametersPagePreview() {
    MATheme {
        ProfileParametersPage()
    }
}
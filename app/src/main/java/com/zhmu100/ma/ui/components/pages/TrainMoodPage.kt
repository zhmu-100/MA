package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.viewModel.TrainingViewModel
import com.zhmu100.ma.ui.components.buttons.BackIconButton
import com.zhmu100.ma.ui.components.buttons.SquareIconButton
import com.zhmu100.ma.ui.components.inputs.MultiLineTextField
import com.zhmu100.ma.ui.data.MoodOption
import com.zhmu100.ma.ui.data.MoodOption.Companion.moods
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainMoodPage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    trainViewModel: TrainingViewModel = koinViewModel()
) {
    BasePage(false, modifier = modifier) { baseModifier ->
        var moodIndex by remember { mutableIntStateOf(-1) }
        var commentValue by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            HeaderSection(
                moodIndex = moodIndex,
                onBackClick = { navController?.popBackStack() },
                onSaveClick = {
                    trainViewModel.currentWorkout.value?.let {
                        trainViewModel.saveWorkout(it, moods[moodIndex].emotion, commentValue)
                    }
                    navController?.navigate(TrainCategoryScreen)
                }
            )
            MoodSelectionRow(
                moodIndex = moodIndex,
                onMoodSelected = { moodIndex = it }
            )
            MultiLineTextField(
                text = commentValue,
                placeholder = "Добавьте заметку о тренировке",
                onTextChanged = { commentValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .requiredHeightIn(max = 400.dp)
            )
            SquareIconButton(
                Icons.Default.Delete,
                onClick = {
                    trainViewModel.clearTraining()
                    navController?.navigate(TrainCategoryScreen)
                })
        }
    }
}

@Composable
private fun HeaderSection(
    moodIndex: Int,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        BackIconButton(onClick = onBackClick)
        Text(
            text = "Оценка тренировки",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
        if (moodIndex != -1) {
            IconButton(
                onClick = onSaveClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Proceed"
                )
            }
        }
    }
}

@Composable
private fun MoodSelectionRow(
    moodIndex: Int,
    onMoodSelected: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        moods.forEachIndexed { ind, mood ->
            MoodIcon(
                onClick = { onMoodSelected(ind) },
                mood,
                isSelected = moodIndex == ind
            )
        }
    }
}

@Composable
private fun MoodIcon(onClick: () -> Unit, moodOption: MoodOption, isSelected: Boolean) {
    IconButton(onClick, modifier = Modifier.size(60.dp)) {
        Icon(
            painter = painterResource(moodOption.iconId),
            moodOption.description,
            modifier = Modifier.size(60.dp),
            tint = if (isSelected) Color(moodOption.color) else Black
        )
    }
}

@Serializable
object TrainMoodScreen

@Preview(showBackground = true)
@Composable
private fun TrainMoodPagePreview() {
    MATheme {
        TrainMoodPage()
    }
}
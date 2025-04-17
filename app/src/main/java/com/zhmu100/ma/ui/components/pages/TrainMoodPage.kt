package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.components.inputs.MultiLineTextField
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable

@Composable
fun TrainMoodPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    BasePage(false, modifier = modifier) { baseModifier ->
        var moodIndex by remember { mutableIntStateOf(-1) }
        var commentValue by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = baseModifier
        ) {
            HeaderSection(
                moodIndex = moodIndex,
                onBackClick = { navController?.navigate(TrainCategoryScreen) },
                onSaveClick = { navController?.navigate(TrainCategoryScreen) }
            )
            MoodSelectionRow(
                moodIndex = moodIndex,
                onMoodSelected = { moodIndex = it }
            )
            MultiLineTextField(
                text = commentValue,
                placeholder = "Добавьте заметку о тренировке",
                onTextChanged = { commentValue = it },
                modifier = Modifier.fillMaxWidth()
            )
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
            .padding(bottom = 8.dp)
    ) {
        val moods = listOf(
            MoodOption(1, R.drawable.sentiment_very_dissatisfied, "Very bad"),
            MoodOption(2, R.drawable.sentiment_dissatisfied, "Bad"),
            MoodOption(3, R.drawable.sentiment_neutral, "Ok"),
            MoodOption(4, R.drawable.sentiment_satisfied, "Good"),
            MoodOption(5, R.drawable.sentiment_very_satisfied, "Very good")
        )

        moods.forEach { mood ->
            MoodIcon(
                onClick = { onMoodSelected(mood.index) },
                iconId = mood.iconId,
                desc = mood.description,
                isSelected = moodIndex == mood.index
            )
        }
    }
}

@Composable
private fun MoodIcon(onClick: () -> Unit, iconId: Int, desc: String, isSelected: Boolean) {
    IconButton(onClick, modifier = Modifier.size(60.dp)) {
        Icon(
            painter = painterResource(iconId),
            desc,
            modifier = Modifier.size(60.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.tertiary else Black
        )
    }
}

private data class MoodOption(
    val index: Int,
    val iconId: Int,
    val description: String
)

@Serializable
object TrainMoodScreen

@Preview(showBackground = true)
@Composable
private fun TrainMoodPagePreview() {
    MATheme {
        TrainMoodPage()
    }
}
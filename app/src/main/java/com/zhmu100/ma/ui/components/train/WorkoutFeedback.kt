package com.zhmu100.ma.ui.components.train

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.domain.model.training.Exercise
import com.zhmu100.ma.domain.model.training.ExerciseName
import com.zhmu100.ma.domain.model.training.ExerciseType
import com.zhmu100.ma.ui.components.inputs.MultiLineTextField
import com.zhmu100.ma.ui.data.MoodOption
import com.zhmu100.ma.ui.theme.MATheme
import java.time.Duration

@Composable
fun WorkoutFeedback(exercise: Exercise) {
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

@Preview(showBackground = true)
@Composable
private fun WorkoutFeedbackPreview() {
    MATheme {
        WorkoutFeedback(
            Exercise(
                name = ExerciseName.PULLUPS,
                duration = Duration.ofSeconds(130).toString(),
                exerciseType = ExerciseType.STATIC
            )
        )
    }
}
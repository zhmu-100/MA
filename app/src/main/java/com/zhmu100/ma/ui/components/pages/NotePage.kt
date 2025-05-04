package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.viewModel.NoteViewModel
import com.zhmu100.ma.domain.viewModel.ViewState
import com.zhmu100.ma.ui.data.Note
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Composable
fun NotePage(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    noteId: String? = null,
    viewModel: NoteViewModel = koinViewModel()
) {
    val noteState by viewModel.currentNoteState.collectAsState()
    var note by remember { mutableStateOf<Note?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (!noteId.isNullOrBlank()) {
            viewModel.getNoteById(noteId)
        } else {
            note = Note()
        }
    }

    LaunchedEffect(noteState) {
        if (noteId.isNullOrBlank()) return@LaunchedEffect

        if (noteState is ViewState.Success) {
            val data = (noteState as ViewState.Success<com.zhmu100.ma.domain.model.Note>).data
            note = Note(
                id = data.id.toString(),
                title = data.title ?: "",
                content = data.content ?: ""
            )
        }
    }

    if (note == null) {
        return
    }

    BasePage(false, modifier = modifier) { baseModifier ->
        Box(modifier = baseModifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = { navController?.navigateUp() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }
                    

                    TextField(
                        value = note!!.title,
                        onValueChange = {
                            note = note!!.copy(title = it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center // центрируем текст
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    

                    IconButton(
                        onClick = { showDeleteConfirmation = true },
                        enabled = noteId != null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFD98B60))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить",
                            tint = Color.White
                        )
                    }
                }
                

                TextField(
                    value = note!!.content,
                    onValueChange = {
                        note = note!!.copy(content = it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Введите текст заметки") }
                )
                

            }
            

            FloatingActionButton(
                onClick = {
                    if (noteId != null) {
                        viewModel.updateNote(noteId, note!!.title, note!!.content)
                    } else {
                        viewModel.createNote(note!!.title, note!!.content)
                    }
                    navController?.navigateUp()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(56.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = Color.White
                )
            }
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Удаление заметки") },
                text = { Text("Вы уверены, что хотите удалить эту заметку?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDeleteConfirmation = false
                        viewModel.deleteNote(noteId!!)
                        navController?.navigateUp()
                    }) {
                        Text("Удалить", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

@Serializable
object NoteScreen

@Preview(showBackground = true)
@Composable
private fun NotePagePreview() {
    MATheme {
        NotePage()
    }
}

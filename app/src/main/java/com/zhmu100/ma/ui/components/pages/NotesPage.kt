package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.ThemedIconButton
import com.zhmu100.ma.ui.data.Note
import com.zhmu100.ma.ui.theme.MATheme
import kotlinx.serialization.Serializable


@Composable
fun NotesPage(modifier: Modifier = Modifier, navController: NavController? = null) {

    val notesList = remember {
        mutableStateOf(
            listOf(
                Note(
                    title = "Заметка",
                    content = "Краткий текст заметки Краткий текст заметки Краткий текст заметки Краткий текст заметки",
                    createdAt = System.currentTimeMillis() - 86400000
                ),
                Note(
                    title = "Заметка",
                    content = "Краткий текст заметки Краткий текст заметки Краткий текст заметки Краткий текст заметки",
                    createdAt = System.currentTimeMillis() - 172800000
                ),
                Note(
                    title = "Заметка",
                    content = "Краткий текст заметки Краткий текст заметки Краткий текст заметки Краткий текст заметки",
                    createdAt = System.currentTimeMillis() - 259200000
                ),
                Note(
                    title = "Заметка",
                    content = "Краткий текст заметки Краткий текст заметки Краткий текст заметки Краткий текст заметки",
                    createdAt = System.currentTimeMillis() - 345600000
                )
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredNotes = if (searchQuery.isEmpty()) {
        notesList.value
    } else {
        notesList.value.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.content.contains(searchQuery, ignoreCase = true)
        }
    }

    BasePage(true, navIndex = 3, modifier = modifier) { baseModifier ->
        Column(
            modifier = baseModifier.fillMaxSize()
        ) {

            Text(
                text = "Заметки",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )


            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = { Text("Поиск") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Заметки",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                

                ThemedIconButton(
                    imageVector = Icons.Default.Add,
                    onClick = {

                        val newNote = Note()
                        notesList.value = notesList.value + newNote
                        navController?.navigate("$NoteScreen/${newNote.id}")
                    }
                )
            }


            if (filteredNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isEmpty()) "У вас пока нет заметок" else "Заметки не найдены",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredNotes) { note ->
                        NoteItem(
                            note = note,
                            onClick = {
                                // Передаем ID заметки в качестве аргумента навигации
                                navController?.navigate("$NoteScreen/${note.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            

            Text(
                text = note.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            

            TextButton(
                onClick = onClick,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "см дальше",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Serializable
object NotesScreen

@Preview(showBackground = true)
@Composable
private fun NotesPagePreview() {
    MATheme {
        NotesPage()
    }
}

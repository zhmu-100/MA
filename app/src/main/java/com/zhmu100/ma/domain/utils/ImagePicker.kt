package com.zhmu100.ma.domain.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Запоминаемый метод для выбора изображения из галереи
 *
 * @param onImageSelected Колбэк, вызываемый при успешном выборе изображения.
 * Принимает:
 * - [ByteArray] - байтовое представление изображения
 * - [String] - имя файла
 * - [String] - MIME-тип изображения
 * @return Функция для запуска пикера изображений
 */
@Composable
fun rememberImagePicker(
    onImageSelected: (ByteArray, String, String) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { processImageUri(context, it, onImageSelected) }
        }
    )
    return { galleryLauncher.launch("image/*") }
}

/**
 * Обрабатывает URI выбранного изображения
 *
 * @param context Контекст приложения
 * @param uri URI выбранного изображения
 * @param onImageSelected Колбэк для передачи результатов обработки
 */
private fun processImageUri(
    context: Context,
    uri: Uri,
    onImageSelected: (ByteArray, String, String) -> Unit
) {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        val fileName = getFileName(context, uri)
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"

        if (bytes != null && fileName != null) {
            onImageSelected(bytes, fileName, mimeType)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Получает имя файла из URI
 *
 * @param context Контекст приложения
 * @param uri URI файла
 * @return Имя файла или null, если не удалось определить
 */
private fun getFileName(context: Context, uri: Uri): String? {
    return when (uri.scheme) {
        "content" -> {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                } else null
            }
        }
        "file" -> uri.lastPathSegment
        else -> null
    }
}
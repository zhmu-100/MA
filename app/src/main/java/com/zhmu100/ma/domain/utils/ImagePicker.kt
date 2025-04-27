package com.zhmu100.ma.domain.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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
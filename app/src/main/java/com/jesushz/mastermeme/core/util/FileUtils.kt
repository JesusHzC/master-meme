package com.jesushz.mastermeme.core.util

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {

    /**
     * Loads an image from the specified file path and converts it to an ImageBitmap.
     *
     * @param path The file path of the image to load.
     * @return An ImageBitmap representation of the image, or null if the image could not be loaded.
     */
    fun loadImageBitmapFromPath(path: String): ImageBitmap? {
        val bitmap = BitmapFactory.decodeFile(path)
        return bitmap?.asImageBitmap()
    }

    /**
     * Converts a file path to a URI using FileProvider.
     *
     * This is useful for sharing files between apps or accessing them in a secure way.
     *
     * @param context The context used to access the FileProvider.
     * @param path The file path to convert to a URI.
     * @return The URI representation of the file path.
     */
    fun pathToUri(context: Context, path: String): Uri {
        val file = File(path)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        return uri
    }

}
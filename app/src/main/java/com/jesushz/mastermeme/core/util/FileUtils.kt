package com.jesushz.mastermeme.core.util

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

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

}
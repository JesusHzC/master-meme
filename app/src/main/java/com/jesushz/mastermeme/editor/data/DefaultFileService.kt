package com.jesushz.mastermeme.editor.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jesushz.mastermeme.editor.domain.FileService
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DefaultFileService(
    private val context: Context,
): FileService {

    override fun saveBitmapToFile(
        bitmap: Bitmap,
        fileName: String
    ): String? {
        val file = File(context.filesDir, "MasterMeme")
        if (!file.exists()) file.mkdirs()

        val imageFile = File(file, "$fileName.png")
        return try {
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun getBitmapFromFile(fileName: String): Bitmap? {
        val file = File(context.filesDir, "MasterMeme")
        if (!file.exists()) return null

        val imageFile = File(file, "$fileName.png")
        return if (imageFile.exists()) {
            BitmapFactory.decodeFile(imageFile.absolutePath)
        } else {
            null
        }
    }

    override fun deleteFile(fileName: String): Boolean {
        val file = File(context.filesDir, "MasterMeme")
        if (!file.exists()) return false

        val imageFile = File(file, "$fileName.png")
        return if (imageFile.exists()) {
            imageFile.delete()
        } else {
            false
        }
    }

}
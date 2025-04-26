package com.jesushz.mastermeme.editor.domain

import android.graphics.Bitmap

interface FileService {
    fun saveBitmapToFile(bitmap: Bitmap, fileName: String): String?
    fun getBitmapFromFile(fileName: String): Bitmap?
    fun deleteFile(fileName: String): Boolean
}
package com.jesushz.mastermeme.editor.domain.use_case

import android.graphics.Bitmap
import com.jesushz.mastermeme.core.database.entity.MemeEntity
import com.jesushz.mastermeme.editor.domain.FileService
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource

class SaveMemeUseCase(
    private val dataSource: LocalMemeDataSource,
    private val fileService: FileService
) {

    suspend operator fun invoke(image: Bitmap): Result<Unit> {
        val name = "meme_${System.currentTimeMillis()}"
        fileService.saveBitmapToFile(
            bitmap = image,
            fileName = name
        )?.let { path ->
            val meme = MemeEntity(
                name = name,
                path = path,
            )
            return dataSource.upsertMeme(meme)
        } ?: return Result.failure(Exception("Error saving meme"))
    }

}

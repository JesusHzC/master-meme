package com.jesushz.mastermeme.editor.domain.use_case

import com.jesushz.mastermeme.core.database.entity.MemeEntity
import com.jesushz.mastermeme.editor.domain.FileService
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource

class DeleteMemeUseCase(
    private val dataSource: LocalMemeDataSource,
    private val fileService: FileService,
) {

    suspend operator fun invoke(meme: MemeEntity) {
        fileService.deleteFile(meme.path)
        dataSource.deleteMeme(meme)
    }

}
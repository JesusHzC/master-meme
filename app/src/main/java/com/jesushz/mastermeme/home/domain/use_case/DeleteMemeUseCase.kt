package com.jesushz.mastermeme.home.domain.use_case

import com.jesushz.mastermeme.editor.domain.FileService
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource
import com.jesushz.mastermeme.home.domain.Meme

class DeleteMemeUseCase(
    private val dataSource: LocalMemeDataSource,
    private val fileService: FileService,
) {

    suspend operator fun invoke(meme: Meme) {
        fileService.deleteFile(meme.path)
        dataSource.deleteMeme(meme.id)
    }

}
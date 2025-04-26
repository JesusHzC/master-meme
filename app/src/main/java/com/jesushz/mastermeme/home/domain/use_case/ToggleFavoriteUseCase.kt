package com.jesushz.mastermeme.home.domain.use_case

import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource

class ToggleFavoriteUseCase(
    private val dataSource: LocalMemeDataSource,
) {

    suspend operator fun invoke(memeId: String) {
        dataSource.toggleFavorite(memeId)
    }

}
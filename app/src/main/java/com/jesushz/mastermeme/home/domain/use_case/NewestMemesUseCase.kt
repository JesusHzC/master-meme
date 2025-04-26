package com.jesushz.mastermeme.home.domain.use_case

import com.jesushz.mastermeme.core.database.entity.MemeEntity
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource
import kotlinx.coroutines.flow.Flow

class NewestMemesUseCase(
    private val dataSource: LocalMemeDataSource
) {

    operator fun invoke(): Flow<List<MemeEntity>> {
        return dataSource.getNewestMemes()
    }

}

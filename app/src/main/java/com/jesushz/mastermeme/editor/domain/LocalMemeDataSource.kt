package com.jesushz.mastermeme.editor.domain

import com.jesushz.mastermeme.core.database.entity.MemeEntity
import kotlinx.coroutines.flow.Flow

interface LocalMemeDataSource {

    suspend fun upsertMeme(meme: MemeEntity): Result<Unit>
    fun getNewestMemes(): Flow<List<MemeEntity>>
    fun getFavoriteMemes(): Flow<List<MemeEntity>>
    suspend fun deleteMeme(meme: MemeEntity)
    suspend fun toggleFavorite(memeId: String)

}

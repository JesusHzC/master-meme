package com.jesushz.mastermeme.core.database

import android.database.sqlite.SQLiteFullException
import com.jesushz.mastermeme.core.database.dao.MemeDao
import com.jesushz.mastermeme.core.database.entity.MemeEntity
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource
import kotlinx.coroutines.flow.Flow

class RoomLocalMemeDataSource(
    private val memeDao: MemeDao
): LocalMemeDataSource {

    override suspend fun upsertMeme(meme: MemeEntity): Result<Unit> {
        return try {
            memeDao.upsertMeme(meme)
            Result.success(Unit)
        } catch (e: SQLiteFullException) {
            Result.failure(e)
        }
    }

    override fun getNewestMemes(): Flow<List<MemeEntity>> {
        return memeDao.getNewestMemes()
    }

    override fun getFavoriteMemes(): Flow<List<MemeEntity>> {
        return memeDao.getFavoriteMemes()
    }

    override suspend fun deleteMeme(memeId: String) {
        return memeDao.deleteMeme(memeId)
    }

    override suspend fun toggleFavorite(memeId: String) {
        return memeDao.toggleFavorite(memeId)
    }

}
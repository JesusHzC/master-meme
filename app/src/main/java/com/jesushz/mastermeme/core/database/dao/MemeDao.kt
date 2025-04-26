package com.jesushz.mastermeme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jesushz.mastermeme.core.database.entity.MemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {

    @Upsert
    suspend fun upsertMeme(meme: MemeEntity)

    @Query("SELECT * FROM memeentity ORDER BY timestamp DESC")
    fun getNewestMemes(): Flow<List<MemeEntity>>

    @Query("SELECT * FROM memeentity WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteMemes(): Flow<List<MemeEntity>>

    @Delete
    suspend fun deleteMeme(meme: MemeEntity)

    @Query("UPDATE MemeEntity SET isFavorite = NOT isFavorite WHERE id = :memeId")
    suspend fun toggleFavorite(memeId: String)

}
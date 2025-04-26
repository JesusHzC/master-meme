package com.jesushz.mastermeme.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesushz.mastermeme.core.database.dao.MemeDao
import com.jesushz.mastermeme.core.database.entity.MemeEntity

@Database(
    entities = [
        MemeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MasterMemeDatabase: RoomDatabase() {

    abstract val memeDao: MemeDao

}

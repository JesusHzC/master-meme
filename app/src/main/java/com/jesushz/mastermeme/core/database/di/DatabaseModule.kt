package com.jesushz.mastermeme.core.database.di

import androidx.room.Room
import com.jesushz.mastermeme.core.database.MasterMemeDatabase
import com.jesushz.mastermeme.core.database.RoomLocalMemeDataSource
import com.jesushz.mastermeme.editor.domain.LocalMemeDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
                androidApplication(),
                MasterMemeDatabase::class.java,
                "mastermeme.db"
            ).fallbackToDestructiveMigration(false).build()
    }
    single { get<MasterMemeDatabase>().memeDao }

    singleOf(::RoomLocalMemeDataSource).bind<LocalMemeDataSource>()
}

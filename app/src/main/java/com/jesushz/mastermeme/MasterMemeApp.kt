package com.jesushz.mastermeme

import android.app.Application
import com.jesushz.mastermeme.editor.di.editorModule
import com.jesushz.mastermeme.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MasterMemeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MasterMemeApp)
            modules(homeModule, editorModule)
        }
    }

}

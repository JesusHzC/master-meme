package com.jesushz.mastermeme.editor.di

import com.jesushz.mastermeme.editor.data.DefaultFileService
import com.jesushz.mastermeme.editor.domain.FileService
import com.jesushz.mastermeme.editor.domain.use_case.DeleteMemeUseCase
import com.jesushz.mastermeme.editor.domain.use_case.FavoritesMemesUseCase
import com.jesushz.mastermeme.editor.domain.use_case.NewestMemesUseCase
import com.jesushz.mastermeme.editor.domain.use_case.SaveMemeUseCase
import com.jesushz.mastermeme.editor.domain.use_case.ToggleFavoriteUseCase
import com.jesushz.mastermeme.editor.presentation.EditorViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val editorModule = module {
    viewModelOf(::EditorViewModel)

    singleOf(::DefaultFileService).bind<FileService>()

    single {
        SaveMemeUseCase(get(), get())
    }
    single {
        DeleteMemeUseCase(get(), get())
    }
    single {
        NewestMemesUseCase(get())
    }
    single {
        FavoritesMemesUseCase(get())
    }
    single {
        ToggleFavoriteUseCase(get())
    }
}

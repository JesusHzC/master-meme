package com.jesushz.mastermeme.editor.di

import com.jesushz.mastermeme.editor.presentation.EditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val editorModule = module {
    viewModelOf(::EditorViewModel)
}

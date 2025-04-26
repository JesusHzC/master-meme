package com.jesushz.mastermeme.home.di

import com.jesushz.mastermeme.home.domain.use_case.DeleteMemeUseCase
import com.jesushz.mastermeme.home.domain.use_case.FavoritesMemesUseCase
import com.jesushz.mastermeme.home.domain.use_case.NewestMemesUseCase
import com.jesushz.mastermeme.home.domain.use_case.ToggleFavoriteUseCase
import com.jesushz.mastermeme.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)

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

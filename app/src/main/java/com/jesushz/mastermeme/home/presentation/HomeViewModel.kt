@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jesushz.mastermeme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.mastermeme.core.database.mappers.toMeme
import com.jesushz.mastermeme.home.data.models.DropdownMenu
import com.jesushz.mastermeme.home.domain.use_case.FavoritesMemesUseCase
import com.jesushz.mastermeme.home.domain.use_case.NewestMemesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val newestMemesUseCase: NewestMemesUseCase,
    private val favoritesMemesUseCase: FavoritesMemesUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            getFavoritesMemes()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _state.value
        )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnFabButtonClick -> {
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }
            }
            HomeAction.OnDismissBottomSheet -> {
                _state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            }
            is HomeAction.OnDropDownMenuSelected -> {
                _state.update {
                    it.copy(
                        dropdownIsExpanded = false,
                        menuSelected = action.menu
                    )
                }
                when (action.menu) {
                    DropdownMenu.FAVORITES -> {
                        getFavoritesMemes()
                    }
                    DropdownMenu.NEWEST -> {
                        getNewestMemes()
                    }
                }
            }
            HomeAction.OnDropdownMenuDismiss -> {
                _state.update {
                    it.copy(
                        dropdownIsExpanded = false
                    )
                }
            }
            HomeAction.OnDropdownMenuExpanded -> {
                _state.update {
                    it.copy(
                        dropdownIsExpanded = true
                    )
                }
            }
            else -> Unit
        }
    }

    private fun getNewestMemes() {
        newestMemesUseCase.invoke()
            .mapLatest {
                it.map { meme ->
                    meme.toMeme()
                }
            }
            .onEach { memes ->
                _state.update {
                    it.copy(
                        memesList = memes
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getFavoritesMemes() {
        favoritesMemesUseCase.invoke()
            .mapLatest {
                it.map { meme ->
                    meme.toMeme()
                }
            }
            .onEach { memes ->
                _state.update {
                    it.copy(
                        memesList = memes
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jesushz.mastermeme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.mastermeme.core.database.mappers.toMeme
import com.jesushz.mastermeme.home.data.models.DropdownMenu
import com.jesushz.mastermeme.home.domain.Meme
import com.jesushz.mastermeme.home.domain.use_case.DeleteMemeUseCase
import com.jesushz.mastermeme.home.domain.use_case.FavoritesMemesUseCase
import com.jesushz.mastermeme.home.domain.use_case.NewestMemesUseCase
import com.jesushz.mastermeme.home.domain.use_case.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newestMemesUseCase: NewestMemesUseCase,
    private val favoritesMemesUseCase: FavoritesMemesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val deleteMemesUseCase: DeleteMemeUseCase
): ViewModel() {

    private var memesCacheList: List<Meme> = emptyList()

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (memesCacheList.isEmpty()) {
                getFavoritesMemes()
            }
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
            is HomeAction.OnToggleFavorite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    toggleFavoriteUseCase.invoke(action.memeId)
                }
            }
            is HomeAction.OnDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    action.memes.forEach { meme ->
                        deleteMemesUseCase.invoke(meme)
                    }
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
                memesCacheList = memes
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
                memesCacheList = memes
            }
            .launchIn(viewModelScope)
    }

}

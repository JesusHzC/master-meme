package com.jesushz.mastermeme.home.presentation

import androidx.lifecycle.ViewModel
import com.jesushz.mastermeme.home.data.models.DropdownMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

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
                        // TODO()
                    }
                    DropdownMenu.NEWEST -> {
                        // TODO()
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
        }
    }

}

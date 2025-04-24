package com.jesushz.mastermeme.home.presentation

import com.jesushz.mastermeme.home.data.models.DropdownMenu

sealed interface HomeAction {

    data object OnFabButtonClick: HomeAction
    data object OnDismissBottomSheet: HomeAction
    data class OnDropDownMenuSelected(val menu: DropdownMenu): HomeAction
    data object OnDropdownMenuExpanded: HomeAction
    data object OnDropdownMenuDismiss: HomeAction

}
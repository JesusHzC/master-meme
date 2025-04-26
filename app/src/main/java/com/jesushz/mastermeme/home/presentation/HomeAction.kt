package com.jesushz.mastermeme.home.presentation

import com.jesushz.mastermeme.home.data.MemeTemplate
import com.jesushz.mastermeme.home.data.models.DropdownMenu
import com.jesushz.mastermeme.home.domain.Meme

sealed interface HomeAction {

    data object OnFabButtonClick: HomeAction
    data object OnDismissBottomSheet: HomeAction
    data class OnDropDownMenuSelected(val menu: DropdownMenu): HomeAction
    data object OnDropdownMenuExpanded: HomeAction
    data object OnDropdownMenuDismiss: HomeAction
    data class OnTemplateSelected(val template: MemeTemplate): HomeAction
    data class OnToggleFavorite(val memeId: String): HomeAction
    data class OnShareClick(val memes: List<Meme>): HomeAction
    data class OnDeleteClick(val memes: List<Meme>): HomeAction

}
package com.jesushz.mastermeme.home.presentation

import com.jesushz.mastermeme.home.data.models.DropdownMenu
import com.jesushz.mastermeme.home.domain.Meme

data class HomeState(
    val showBottomSheet: Boolean = false,
    val dropdownIsExpanded: Boolean = false,
    val menuSelected: DropdownMenu = DropdownMenu.FAVORITES,
    val memesList: List<Meme> = emptyList(),
)

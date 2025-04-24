package com.jesushz.mastermeme.home.presentation

import com.jesushz.mastermeme.home.data.models.DropdownMenu

data class HomeState(
    val showBottomSheet: Boolean = false,
    val dropdownIsExpanded: Boolean = false,
    val menuSelected: DropdownMenu = DropdownMenu.FAVORITES,

)

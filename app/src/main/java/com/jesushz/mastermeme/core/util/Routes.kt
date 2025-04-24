package com.jesushz.mastermeme.core.util

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

sealed interface Routes {

    // Graphs
    @Serializable
    data object HomeGraph: Routes

    // Screens
    @Serializable
    data object HomeScreen: Routes

    @Serializable
    data class EditorScreen(
        @DrawableRes val templateImage: Int
    ): Routes

}
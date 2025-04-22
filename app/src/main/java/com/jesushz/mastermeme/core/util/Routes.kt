package com.jesushz.mastermeme.core.util

import kotlinx.serialization.Serializable

sealed interface Routes {

    // Graphs
    @Serializable
    data object HomeGraph: Routes
    @Serializable
    data object EditorGraph: Routes

    // Screens
    @Serializable
    data object HomeScreen: Routes

}
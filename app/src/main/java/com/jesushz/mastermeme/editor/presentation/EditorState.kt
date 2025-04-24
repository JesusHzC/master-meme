package com.jesushz.mastermeme.editor.presentation

import androidx.annotation.DrawableRes

data class EditorState(
    @DrawableRes val templateImage: Int? = null,
    val showTextActions: Boolean = false,
)

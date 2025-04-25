package com.jesushz.mastermeme.editor.presentation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesushz.mastermeme.editor.data.EditorFonts

data class EditorState(
    @DrawableRes val templateImage: Int? = null,
    val showTextActions: Boolean = false,
    val fontSelected: EditorFonts = EditorFonts.ROBOTO,
    val fontSizeSelected: Float = 30f,
    val fontSizeSelectedDp: Dp = 30.dp,
    val colorSelected: Color = Color.White,
)

package com.jesushz.mastermeme.editor.presentation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jesushz.mastermeme.editor.data.EditorFonts
import com.jesushz.mastermeme.editor.data.EditorTextField

data class EditorState(
    @DrawableRes val templateImage: Int? = null,
    val showTextActions: Boolean = false,
    val fontSelected: EditorFonts = EditorFonts.ROBOTO,
    val fontSizeSelected: Float = 30f,
    val fontSizeSelectedSp: TextUnit = 30.sp,
    val colorSelected: Color = Color.White,
    val textFieldList: List<EditorTextField> = emptyList(),
    val currentTextField: EditorTextField? = null
)

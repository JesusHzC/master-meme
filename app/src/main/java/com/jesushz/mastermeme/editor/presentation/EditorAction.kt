package com.jesushz.mastermeme.editor.presentation

import androidx.compose.ui.graphics.Color
import com.jesushz.mastermeme.editor.data.EditorFonts
import com.jesushz.mastermeme.editor.data.EditorTextField

sealed interface EditorAction {
    data object OnAddTextClick: EditorAction
    data object OnSaveMemeClick: EditorAction
    data object OnClearTextClick: EditorAction
    data object OnSaveTextClick: EditorAction
    data object OnBackClick: EditorAction
    data class OnFontSelected(val font: EditorFonts): EditorAction
    data class OnFontSizeSelected(val fontSize: Float): EditorAction
    data class OnColorSelected(val color: Color): EditorAction
    data class OnTextFieldClick(val textField: EditorTextField): EditorAction
    data class OnDeleteTextField(val textField: EditorTextField): EditorAction
}

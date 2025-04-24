package com.jesushz.mastermeme.editor.presentation

sealed interface EditorAction {
    data object OnAddTextClick: EditorAction
    data object OnSaveMemeClick: EditorAction
    data object OnClearTextClick: EditorAction
    data object OnSaveTextClick: EditorAction
    data object OnBackClick: EditorAction
}

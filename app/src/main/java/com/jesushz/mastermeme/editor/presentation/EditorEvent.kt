package com.jesushz.mastermeme.editor.presentation

sealed interface EditorEvent {

    data object OnSaveMemeSuccess: EditorEvent
    data class OnSaveMemeError(val error: String): EditorEvent

}

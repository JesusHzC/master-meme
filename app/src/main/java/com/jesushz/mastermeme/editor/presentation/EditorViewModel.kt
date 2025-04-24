package com.jesushz.mastermeme.editor.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.jesushz.mastermeme.core.util.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditorViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(EditorState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                templateImage = savedStateHandle
                    .toRoute<Routes.EditorScreen>().templateImage
            )
        }
    }

    fun onAction(action: EditorAction) {
        when (action) {
            EditorAction.OnAddTextClick -> {
                _state.update {
                    it.copy(
                        showTextActions = true
                    )
                }
            }
            EditorAction.OnBackClick -> {
                TODO()
            }
            EditorAction.OnClearTextClick -> {
                TODO()
            }
            EditorAction.OnSaveMemeClick -> {
                TODO()
            }
            EditorAction.OnSaveTextClick -> {
                _state.update {
                    it.copy(
                        showTextActions = false
                    )
                }
            }
        }
    }

}

package com.jesushz.mastermeme.editor.presentation

import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jesushz.mastermeme.core.util.Routes
import com.jesushz.mastermeme.editor.data.EditorTextField
import com.jesushz.mastermeme.editor.domain.use_case.SaveMemeUseCase
import com.jesushz.mastermeme.editor.presentation.EditorEvent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(
    savedStateHandle: SavedStateHandle,
    private val saveMemeUseCase: SaveMemeUseCase
): ViewModel() {

    private val _state = MutableStateFlow(EditorState())
    val state = _state.asStateFlow()

    private val _event = Channel<EditorEvent>()
    val event = _event.receiveAsFlow()

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
                val currentTextField = EditorTextField(
                    textStyle = state.value.fontSelected,
                    textColor = state.value.colorSelected,
                    textSize = state.value.fontSizeSelectedSp
                )
                _state.update {
                    it.copy(
                        showTextActions = true,
                        textFieldList = it.textFieldList + currentTextField,
                        currentTextField = currentTextField
                    )
                }
            }
            EditorAction.OnBackClick -> {
                _state
                    .update {
                        it.copy(
                            showLeaveDialog = true
                        )
                    }
            }
            EditorAction.OnClearTextClick -> {
                _state.update {
                    it.copy(
                        textFieldList = it.textFieldList.toMutableList().apply {
                            if (this.isNotEmpty()) {
                                removeAt(size - 1)
                            }
                        },
                        currentTextField = it.textFieldList.lastOrNull(),
                        showTextActions = it.textFieldList.size > 1
                    )
                }
            }
            is EditorAction.OnSaveMemeClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveMemeUseCase.invoke(action.image)
                        .fold(
                            onSuccess = {
                                withContext(Dispatchers.Main) {
                                    _event.send(OnSaveMemeSuccess)
                                }
                            },
                            onFailure = {
                                withContext(Dispatchers.Main) {
                                    _event.send(
                                        OnSaveMemeError(it.message ?: "Unknown error")
                                    )
                                }
                            }
                        )
                }
            }
            EditorAction.OnSaveTextClick -> {
                _state.update {
                    it.copy(
                        showTextActions = false
                    )
                }
            }
            is EditorAction.OnColorSelected -> {
                _state.update {
                    it.copy(
                        colorSelected = action.color,
                        currentTextField = it.currentTextField?.copy(
                            textColor = action.color
                        ),
                        textFieldList = it.textFieldList.toMutableList().apply {
                            val findById = it.textFieldList.find { textField ->
                                textField.id == it.currentTextField?.id
                            }
                            findById?.let { findItem ->
                                val index = it.textFieldList.indexOf(findById)
                                this[index] = findById.copy(
                                    textColor = action.color
                                )
                            }
                        }
                    )
                }
            }
            is EditorAction.OnFontSelected -> {
                _state.update {
                    it.copy(
                        fontSelected = action.font,
                        currentTextField = it.currentTextField?.copy(
                            textStyle = action.font
                        ),
                        textFieldList = it.textFieldList.toMutableList().apply {
                            val findById = it.textFieldList.find { textField ->
                                textField.id == it.currentTextField?.id
                            }
                            findById?.let { findItem ->
                                val index = it.textFieldList.indexOf(findById)
                                this[index] = findById.copy(
                                    textStyle = action.font
                                )
                            }
                        }
                    )
                }
            }
            is EditorAction.OnFontSizeSelected -> {
                _state.update {
                    it.copy(
                        fontSizeSelected = action.fontSize,
                        fontSizeSelectedSp = action.fontSize.sp,
                        currentTextField = it.currentTextField?.copy(
                            textSize = action.fontSize.sp
                        ),
                        textFieldList = it.textFieldList.toMutableList().apply {
                            val findById = it.textFieldList.find { textField ->
                                textField.id == it.currentTextField?.id
                            }
                            findById?.let { findItem ->
                                val index = it.textFieldList.indexOf(findById)
                                this[index] = findById.copy(
                                    textSize = action.fontSize.sp
                                )
                            }
                        }
                    )
                }
            }
            is EditorAction.OnDeleteTextField -> {
                _state.update {
                    it.copy(
                        textFieldList = it.textFieldList.toMutableList().apply {
                            remove(action.textField)
                        },
                        currentTextField = it.textFieldList.lastOrNull(),
                        showTextActions = it.textFieldList.size > 1
                    )
                }
            }
            is EditorAction.OnTextFieldClick -> {
                _state.update {
                    it.copy(
                        currentTextField = action.textField
                    )
                }
            }

            EditorAction.OnLeaveDialogDismiss -> {
                _state
                    .update {
                        it.copy(
                            showLeaveDialog = false
                        )
                    }
            }

            else -> Unit
        }
    }

}

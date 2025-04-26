package com.jesushz.mastermeme.editor.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.core.presentation.designsystem.components.MasterMemeScaffold
import com.jesushz.mastermeme.core.presentation.designsystem.theme.MasterMemeTheme
import com.jesushz.mastermeme.core.presentation.ui.ObserveAsEvents
import com.jesushz.mastermeme.editor.presentation.components.ButtonActions
import com.jesushz.mastermeme.editor.presentation.components.EditorTopBar
import com.jesushz.mastermeme.editor.presentation.components.EditorView
import com.jesushz.mastermeme.editor.presentation.components.LeaveDialog
import com.jesushz.mastermeme.editor.presentation.components.TextActions
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditorScreenRoot(
    viewModel: EditorViewModel = koinViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(
        flow = viewModel.event
    ) { event ->
        when (event) {
            is EditorEvent.OnSaveMemeError -> {
                Toast
                    .makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
            }
            EditorEvent.OnSaveMemeSuccess -> {
                onNavigateToHome()
            }
        }
    }
    EditorScreen(
        state = state,
        onAction = { action ->
            when (action) {
                EditorAction.OnLeaveDialogConfirm -> onNavigateUp()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun EditorScreen(
    state: EditorState,
    onAction: (EditorAction) -> Unit
) {
    BackHandler {
        onAction(EditorAction.OnBackClick)
    }
    var editorView by remember { mutableStateOf<EditorView?>(null) }

    LaunchedEffect(state.textFieldList) {
        editorView?.updateTextFieldList(state.textFieldList)
    }

    LeaveDialog(
        showDialog = state.showLeaveDialog,
        onDismissRequest = {
            onAction(EditorAction.OnLeaveDialogDismiss)
        },
        onLeave = {
            onAction(EditorAction.OnLeaveDialogDismiss)
            onAction(EditorAction.OnLeaveDialogConfirm)
        }
    )

    MasterMemeScaffold(
        topBar = {
            EditorTopBar(
                onBackClick = {
                    onAction(EditorAction.OnBackClick)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                state.templateImage?.let { template ->
                    AndroidView(
                        factory = { context ->
                            EditorView(
                                context,
                                template,
                                onTextFieldClick = {
                                    onAction(EditorAction.OnTextFieldClick(it))
                                },
                                onDeleteTextField = {
                                    onAction(EditorAction.OnDeleteTextField(it))
                                }
                            ).also {
                                editorView = it
                            }
                        }
                    )
                }
            }
            when {
                state.showTextActions -> {
                    TextActions(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        fontSelected = state.fontSelected,
                        fontSizeSelected = state.fontSizeSelected,
                        colorSelected = state.colorSelected,
                        onFontSelected = {
                            onAction(EditorAction.OnFontSelected(it))
                        },
                        onFontSizeChanged = {
                            onAction(EditorAction.OnFontSizeSelected(it))
                        },
                        onColorSelected = {
                            onAction(EditorAction.OnColorSelected(it))
                        },
                        onClearTextClick = {
                            onAction(EditorAction.OnClearTextClick)
                        },
                        onSaveTextClick = {
                            onAction(EditorAction.OnSaveTextClick)
                        }
                    )
                }
                else -> {
                    ButtonActions(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        onAddTextClick = {
                            onAction(EditorAction.OnAddTextClick)
                        },
                        onSaveMemeClick = {
                            editorView?.captureEditedBitmap()?.let {
                                onAction(
                                    EditorAction.OnSaveMemeClick(it)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditorScreenPreview() {
    MasterMemeTheme {
        EditorScreen(
            state = EditorState(
                templateImage = R.drawable.template_46
            ),
            onAction = {}
        )
    }
}

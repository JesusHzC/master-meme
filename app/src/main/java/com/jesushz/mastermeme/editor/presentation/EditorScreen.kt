package com.jesushz.mastermeme.editor.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.core.presentation.designsystem.components.MasterMemeScaffold
import com.jesushz.mastermeme.core.presentation.designsystem.theme.MasterMemeTheme
import com.jesushz.mastermeme.editor.presentation.components.ButtonActions
import com.jesushz.mastermeme.editor.presentation.components.EditorTopBar
import com.jesushz.mastermeme.editor.presentation.components.TextActions
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditorScreenRoot(
    viewModel: EditorViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditorScreen(
        state = state,
        onAction = { action ->
            when (action) {
                EditorAction.OnBackClick -> onNavigateUp()
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
                    Image(
                        painter = painterResource(id = template),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                max = 800.dp
                            )
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            when {
                state.showTextActions -> {
                    TextActions(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        onClearTextClick = {},
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
                        onSaveMemeClick = {}
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

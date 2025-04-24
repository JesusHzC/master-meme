package com.jesushz.mastermeme.editor.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jesushz.mastermeme.core.presentation.designsystem.theme.MasterMemeTheme

@Composable
fun EditorScreenRoot() {
    EditorScreen()
}

@Composable
private fun EditorScreen() {
    Text("Hola")
}

@Preview
@Composable
private fun EditorScreenPreview() {
    MasterMemeTheme {
        EditorScreen()
    }
}
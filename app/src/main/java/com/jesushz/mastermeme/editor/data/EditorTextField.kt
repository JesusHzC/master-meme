@file:OptIn(ExperimentalUuidApi::class)

package com.jesushz.mastermeme.editor.data

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class EditorTextField(
    val id: String = Uuid.random().toString(),
    val textState: TextFieldState = TextFieldState(),
    val textStyle: EditorFonts = EditorFonts.ROBOTO,
    val textColor: Color = Color.White,
    val textSize: TextUnit = 30.sp,
)

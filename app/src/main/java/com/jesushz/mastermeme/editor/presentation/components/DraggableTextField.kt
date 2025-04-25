package com.jesushz.mastermeme.editor.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.core.presentation.ui.clearFocusOnKeyboardDismiss
import com.jesushz.mastermeme.editor.data.EditorTextField
import kotlin.math.roundToInt

@Composable
fun DraggableTextField(
    modifier: Modifier = Modifier,
    textField: EditorTextField,
    onTextFieldClick: () -> Unit,
    onDeleteTextField: () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            onTextFieldClick()
        }
    }

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .padding(12.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        BasicTextField(
            state = textField.textState,
            textStyle = textField.textStyle.textStyle.copy(
                color = textField.textColor,
                fontSize = textField.textSize,
            ),
            modifier = Modifier
                .clearFocusOnKeyboardDismiss()
                .onFocusChanged { isFocused = it.isFocused }
                .then(
                    if (isFocused) {
                        Modifier
                            .border(
                                color = MaterialTheme.colorScheme.primary,
                                width = 2.dp,
                                shape = MaterialTheme.shapes.small
                            )
                    } else {
                        Modifier
                    }
                ),
            decorator = { innerBox ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (textField.textState.text.isEmpty() && !isFocused) {
                        Text(
                            text = "TAP TO EDIT",
                            style = textField.textStyle.textStyle.copy(
                                color = textField.textColor,
                                fontSize = textField.textSize,
                            ),
                        )
                    }
                    innerBox()
                }
            }
        )
        if (isFocused) {
            IconButton(
                onClick = {
                    onDeleteTextField()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = (10).dp,
                        y = (-8).dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close)
                )
            }
        }
    }
}
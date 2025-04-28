package com.jesushz.mastermeme.editor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
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

    var width by remember { mutableFloatStateOf(200f) }
    var height by remember { mutableFloatStateOf(80f) }

    var rotation by remember { mutableFloatStateOf(0f) }

    var isFocused by remember { mutableStateOf(false) }
    var isResizing by remember { mutableStateOf(false) }
    var isRotating by remember { mutableStateOf(false) }

    val dynamicYOffset = with(LocalDensity.current) {
        -(height * 0.3f).toDp() - 20.dp
    }

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
                    if (isResizing || isRotating) {
                        change.consume()
                    } else {
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
            }
            .graphicsLayer(
                rotationZ = rotation
            )
    ) {
        Box(
            modifier = Modifier
                .size(width.dp, height.dp)
                .background(Color.Transparent)
        ) {
            BasicTextField(
                state = textField.textState,
                textStyle = textField.textStyle.textStyle.copy(
                    color = textField.textColor,
                    fontSize = textField.textSize,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .clearFocusOnKeyboardDismiss()
                    .onFocusChanged { isFocused = it.isFocused }
                    .then(
                        if (isFocused) {
                            Modifier.border(
                                color = MaterialTheme.colorScheme.primary,
                                width = 2.dp,
                                shape = MaterialTheme.shapes.small
                            )
                        } else {
                            Modifier
                        }
                    ),
                cursorBrush = SolidColor(textField.textColor),
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

            // Handle para redimensionar (abajo a la derecha)
            if (isFocused) {
                // BottomEnd -> Aumenta width y height (igual que antes)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.BottomEnd)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isResizing = true },
                                onDragEnd = { isResizing = false },
                                onDragCancel = { isResizing = false },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    width = (width + dragAmount.x).coerceAtLeast(50f)
                                    height = (height + dragAmount.y).coerceAtLeast(30f)
                                }
                            )
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )

                // TopStart -> Decrece width y height, y mueve posición
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.TopStart)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isResizing = true },
                                onDragEnd = { isResizing = false },
                                onDragCancel = { isResizing = false },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    width = (width - dragAmount.x).coerceAtLeast(50f)
                                    height = (height - dragAmount.y).coerceAtLeast(30f)
                                    offsetX += dragAmount.x
                                    offsetY += dragAmount.y
                                }
                            )
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )

                // BottomStart -> Decrece width, aumenta height, mueve solo en X
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.BottomStart)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isResizing = true },
                                onDragEnd = { isResizing = false },
                                onDragCancel = { isResizing = false },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    width = (width - dragAmount.x).coerceAtLeast(50f)
                                    height = (height + dragAmount.y).coerceAtLeast(30f)
                                    offsetX += dragAmount.x
                                }
                            )
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )


                // Handle para rotar (arriba a la derecha)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopCenter)
                        .offset(x = 0.dp, y = dynamicYOffset) // Lo alejamos un poco
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { isRotating = true },
                                onDragEnd = { isRotating = false },
                                onDragCancel = { isRotating = false },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    rotation += dragAmount.x // Aquí rotamos solo en X, puedes combinar X/Y si quieres
                                }
                            )
                        }
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.rotate),
                    )
                }
            }
        }

        // Botón de borrar
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

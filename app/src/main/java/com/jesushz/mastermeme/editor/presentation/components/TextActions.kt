package com.jesushz.mastermeme.editor.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.editor.data.EditorFonts
import com.jesushz.mastermeme.editor.data.editorColors

enum class TextActionType(@DrawableRes val icon: Int) {
    TEXT_STYLE(R.drawable.text_style_button),
    TEXT_SIZE(R.drawable.text_size_button),
    TEXT_COLOR(R.drawable.text_color_button),
}

@Composable
fun TextActions(
    modifier: Modifier = Modifier,
    fontSizeSelected: Float,
    fontSelected: EditorFonts,
    colorSelected: Color,
    onFontSizeChanged: (Float) -> Unit,
    onFontSelected: (EditorFonts) -> Unit,
    onColorSelected: (Color) -> Unit,
    onClearTextClick: () -> Unit,
    onSaveTextClick: () -> Unit
) {
    var showTextStyles by remember { mutableStateOf(false) }
    var showTextSize by remember { mutableStateOf(false) }
    var showTextColor by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AnimatedVisibility(showTextStyles) {
                FontSelector(
                    font = fontSelected,
                    onFontSelected = { selectedFont ->
                        onFontSelected(selectedFont)
                    }
                )
            }

            AnimatedVisibility(showTextSize) {
                FontSizeSlider(
                    fontSize = fontSizeSelected,
                    onFontSizeChanged = { size ->
                        onFontSizeChanged(size)
                    }
                )
            }

            AnimatedVisibility(showTextColor) {
                ColorSelector(
                    colorSelected = colorSelected,
                    onColorClick = { color ->
                        onColorSelected(color)
                    }
                )
            }

            ActionButtons(
                onClearTextClick = onClearTextClick,
                onSaveTextClick = onSaveTextClick,
                onToggle = { action ->
                    when (action) {
                        TextActionType.TEXT_STYLE -> showTextStyles = !showTextStyles
                        TextActionType.TEXT_SIZE -> showTextSize = !showTextSize
                        TextActionType.TEXT_COLOR -> showTextColor = !showTextColor
                    }
                }
            )
        }
    }
}

@Composable
private fun FontSelector(
    font: EditorFonts,
    onFontSelected: (EditorFonts) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(EditorFonts.entries, key = { it.ordinal }) { font ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clickable {
                        onFontSelected(font)
                    }
            ) {
                Text(
                    text = "GOOD",
                    style = font.textStyle.copy(
                        fontSize = if (font == EditorFonts.COMIC) 19.sp
                        else MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = font.displayName,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun FontSizeSlider(
    fontSize: Float,
    onFontSizeChanged: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Aa",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Slider(
            value = fontSize,
            onValueChange = { onFontSizeChanged(it) },
            valueRange = 10f..60f,
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
                .padding(horizontal = 8.dp)
        )
        Text(
            text = "Aa",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ColorSelector(
    colorSelected: Color,
    onColorClick: (Color) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(editorColors, key = { it.hashCode() }) { color ->
            Box(
                modifier = Modifier
                    .size(
                        if (color == colorSelected) 40.dp
                        else 30.dp
                    )
                    .clip(CircleShape)
                    .background(color)
                    .then (
                        if (color == colorSelected) {
                            Modifier
                                .border(
                                    width = 3.dp,
                                    color = when (color) {
                                        Color.Black -> Color.White
                                        else -> Color.Black
                                    },
                                    shape = CircleShape
                                )
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onColorClick(color) }
            )
        }
    }
}

@Composable
private fun ActionButtons(
    onClearTextClick: () -> Unit,
    onSaveTextClick: () -> Unit,
    onToggle: (TextActionType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onClearTextClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextActionType.entries.fastForEach { action ->
                IconButton(onClick = { onToggle(action) }) {
                    Image(
                        painter = painterResource(id = action.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .aspectRatio(1f),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }

        IconButton(onClick = onSaveTextClick) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.save),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


package com.jesushz.mastermeme.editor.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jesushz.mastermeme.R

enum class EditorFonts(
    val displayName: String,
    val textStyle: TextStyle
) {
    IMPACT(
        displayName = "Impact",
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.impact)),
        )
    ),
    ROBOTO(
        displayName = "Roboto",
        textStyle = TextStyle(
            fontFamily = FontFamily.Default,
        )
    ),
    ROBOTO_BOLD(
        displayName = "Roboto Bold",
        textStyle = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold
        )
    ),
    COMIC(
        displayName = "Comic",
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.comic)),
            fontWeight = FontWeight.Bold
        )
    ),
    SHADOWED(
        displayName = "Shadowed",
        textStyle = TextStyle(
            shadow = Shadow(
                offset = Offset(2f, 2f),
                blurRadius = 3f
            ),
        )
    ),
    OUTLINE(
        displayName = "Outline",
        textStyle = TextStyle(
            drawStyle = Stroke(
                width = 3f,
                join = StrokeJoin.Round
            )
        )
    ),
    RETRO(
        displayName = "Retro",
        textStyle = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.sp
        )
    ),
    HANDWRITTEN(
        displayName = "Handwritten",
        textStyle = TextStyle(
            fontFamily = FontFamily.Cursive,
            letterSpacing = 1.sp
        )
    )
}

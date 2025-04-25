package com.jesushz.mastermeme.editor.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import com.jesushz.mastermeme.core.presentation.ui.ImageUtils.getImageSizeFromResource

@SuppressLint("ViewConstructor")
class EditorView(
    context: Context,
    @DrawableRes templateImage: Int
) : FrameLayout(context) {

    init {
        val view = ComposeView(context)
        val sizeTemplateImage = getImageSizeFromResource(context, templateImage) ?: Pair(0, 0)
        val originalWidth = sizeTemplateImage.first
        val originalHeight = sizeTemplateImage.second

        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val scaleFactor = 0.9f

        val targetWidth = (screenWidth * scaleFactor).toInt()
        val aspectRatio = if (originalHeight != 0) originalWidth.toFloat() / originalHeight else 1f
        val targetHeight = (targetWidth / aspectRatio).toInt()

        view.layoutParams = LayoutParams(targetWidth, targetHeight)
        this.addView(view)

        view.setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {
                Image(
                    painter = painterResource(id = templateImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
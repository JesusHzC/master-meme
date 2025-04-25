package com.jesushz.mastermeme.editor.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.fastForEach
import com.jesushz.mastermeme.core.presentation.ui.ImageUtils.getImageSizeFromResource
import androidx.core.graphics.createBitmap
import com.jesushz.mastermeme.editor.data.EditorTextField

@SuppressLint("ViewConstructor")
class EditorView(
    context: Context,
    @DrawableRes templateImage: Int,
    onTextFieldClick: (EditorTextField) -> Unit,
    onDeleteTextField: (EditorTextField) -> Unit
) : FrameLayout(context) {

    private var textFieldList by mutableStateOf(emptyList<EditorTextField>())
    private var composeView: ComposeView = ComposeView(context)

    init {
        val sizeTemplateImage = getImageSizeFromResource(context, templateImage) ?: Pair(0, 0)
        val originalWidth = sizeTemplateImage.first
        val originalHeight = sizeTemplateImage.second

        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val scaleFactor = 0.9f

        val targetWidth = (screenWidth * scaleFactor).toInt()
        val aspectRatio = if (originalHeight != 0) originalWidth.toFloat() / originalHeight else 1f
        val targetHeight = (targetWidth / aspectRatio).toInt()

        composeView.layoutParams = LayoutParams(targetWidth, targetHeight)
        this.addView(composeView)

        composeView.setContent {
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
                textFieldList.fastForEach { textField ->
                    DraggableTextField(
                        textField = textField,
                        onTextFieldClick = {
                            onTextFieldClick(textField)
                        },
                        onDeleteTextField = {
                            onDeleteTextField(textField)
                        }
                    )
                }
            }
        }
    }

    fun updateTextFieldList(newTextFieldList: List<EditorTextField>) {
        textFieldList = newTextFieldList
    }

    fun captureEditedBitmap(): Bitmap? {
        return composeView.captureToBitmap()
    }

    fun View.captureToBitmap(): Bitmap? {
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

}

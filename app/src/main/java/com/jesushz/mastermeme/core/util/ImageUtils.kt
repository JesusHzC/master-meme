package com.jesushz.mastermeme.core.util

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

object ImageUtils {

    /**
     * Retrieves the size of a drawable resource.
     *
     * @param context The context to access resources.
     * @param drawableId The resource ID of the drawable.
     * @return A pair containing the width and height of the drawable, or null if the drawable is not found.
     */
    fun getImageSizeFromResource(context: Context, @DrawableRes drawableId: Int): Pair<Int, Int>? {
        val drawable: Drawable = ResourcesCompat.getDrawable(context.resources, drawableId, null) ?: return null
        val bitmap = (drawable as? BitmapDrawable)?.bitmap
        return bitmap?.let { Pair(it.width, it.height) }
    }

}
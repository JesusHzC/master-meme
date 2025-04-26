package com.jesushz.mastermeme.home.domain

import androidx.compose.ui.graphics.ImageBitmap

data class Meme(
    val id: String,
    val image: ImageBitmap?,
    val isFavorite: Boolean
)
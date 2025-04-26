package com.jesushz.mastermeme.core.database.mappers

import com.jesushz.mastermeme.core.database.entity.MemeEntity
import com.jesushz.mastermeme.core.util.FileUtils
import com.jesushz.mastermeme.home.domain.Meme

fun MemeEntity.toMeme(): Meme {
    return Meme(
        id = id,
        image = FileUtils.loadImageBitmapFromPath(path),
        path = path,
        isFavorite = isFavorite
    )
}

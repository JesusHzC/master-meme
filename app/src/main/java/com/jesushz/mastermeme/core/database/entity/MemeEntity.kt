@file:OptIn(ExperimentalUuidApi::class)

package com.jesushz.mastermeme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity
data class MemeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = Uuid.random().toString(),
    val name: String,
    val path: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

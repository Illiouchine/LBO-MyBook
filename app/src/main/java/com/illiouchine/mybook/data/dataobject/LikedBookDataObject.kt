package com.illiouchine.mybook.data.dataobject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "liked_book",
)
data class LikedBookDataObject(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String?,
    val description: String?,
    val imageUrl: String?,
)

package com.illiouchine.mybook.data.dataobject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "searched_book",
)
data class BookDataObject(
    @PrimaryKey
    val etag: String,
    val title: String,
    val author: String?,
    val description: String?,
    val imageUrl: String?,
)
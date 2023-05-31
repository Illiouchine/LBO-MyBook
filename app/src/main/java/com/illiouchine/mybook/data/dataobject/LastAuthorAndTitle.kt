package com.illiouchine.mybook.data.dataobject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author_title")
data class LastAuthorAndTitle(
    @PrimaryKey
    val author: String,
    val title: String
)

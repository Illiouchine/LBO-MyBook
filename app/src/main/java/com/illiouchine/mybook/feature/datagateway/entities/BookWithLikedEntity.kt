package com.illiouchine.mybook.feature.datagateway.entities

// Todo Merge with BookEntity (
data class BookWithLikedEntity(
    val title: String,
    val author: String,
    val description: String?,
    val imageUrl: String?,
    val liked: Boolean,
)
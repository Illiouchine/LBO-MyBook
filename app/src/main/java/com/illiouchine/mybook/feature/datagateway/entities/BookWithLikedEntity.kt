package com.illiouchine.mybook.feature.datagateway.entities

data class BookWithLikedEntity(
    val title: String,
    val author: String,
    val description: String?,
    val imageUrl: String?,
    val liked: Boolean,
)
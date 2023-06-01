package com.illiouchine.mybook.feature.datagateway.entities

data class BookEntity(
    val id: String,
    val title: String,
    val author: String?,
    val description: String?,
    val imageUrl: String?,
)
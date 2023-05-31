package com.illiouchine.mybook.feature.datagateway.entities

data class SearchResultWithLikedBookEntity(
    val list: List<BookWithLikedEntity>,
    val author: String,
    val title: String,
)


package com.illiouchine.mybook.feature.datagateway.entities

data class SearchResultExtendedEntity(
    val list: List<BookEntity>,
    val author: String,
    val title: String,
)
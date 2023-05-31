package com.illiouchine.mybook.data.dataobject

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

data class SearchResultDataObject(
    val bookList: List<BookEntity>, val author: String, val title: String
)
package com.illiouchine.mybook.data.datasource

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface BookLocalDataSource {
    fun saveSearchResult(bookList: List<BookEntity>, author: String, title: String)
    fun getLastSearchResult(): SearchResult

    data class SearchResult(
        val bookList: List<BookEntity>, val author: String, val title: String
    )
}
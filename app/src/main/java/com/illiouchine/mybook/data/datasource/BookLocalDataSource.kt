package com.illiouchine.mybook.data.datasource

import com.illiouchine.mybook.data.dataobject.SearchResultDataObject
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface BookLocalDataSource {
    fun saveSearchResult(bookList: List<BookEntity>, author: String, title: String)
    fun getLastSearchResult(): SearchResultDataObject
    fun getLikedBooks(): List<BookEntity>


}

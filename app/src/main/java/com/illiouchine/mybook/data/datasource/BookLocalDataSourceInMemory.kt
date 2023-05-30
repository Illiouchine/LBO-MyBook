package com.illiouchine.mybook.data.datasource

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import javax.inject.Inject

class BookLocalDataSourceInMemory @Inject constructor() : BookLocalDataSource {

    private val bookList: MutableList<BookEntity> = mutableListOf()
    private var author: String = ""
    private var title: String = ""


    override fun saveSearchResult(bookList: List<BookEntity>, author: String, title: String) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        this.author = author
        this.title = title
    }

    override fun getLastSearchResult(): BookLocalDataSource.SearchResult {
        return BookLocalDataSource.SearchResult(
            bookList = this.bookList,
            author = this.author,
            title = this.title
        )
    }
}
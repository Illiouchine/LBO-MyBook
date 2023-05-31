package com.illiouchine.mybook.data.datasource

import com.illiouchine.mybook.data.dataobject.SearchResultDataObject
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import javax.inject.Inject

class BookLocalDataSourceInMemory @Inject constructor() : BookLocalDataSource {

    private val bookList: MutableList<BookEntity> = mutableListOf()
    private var author: String = ""
    private var title: String = ""
    private val likedBook: MutableList<BookEntity> = mutableListOf()


    override fun saveSearchResult(bookList: List<BookEntity>, author: String, title: String) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        this.author = author
        this.title = title
    }

    override fun getLastSearchResult(): SearchResultDataObject {
        return SearchResultDataObject(
            bookList = this.bookList,
            author = this.author,
            title = this.title
        )
    }

    override fun getLikedBooks(): List<BookEntity> {
        return likedBook
    }
}
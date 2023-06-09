package com.illiouchine.mybook.feature.datagateway

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultExtendedEntity

interface BookDataGateway {
    suspend fun getBookByAuthorAndTitle(author: String, title: String): List<BookEntity>
    suspend fun getSearchResult(): SearchResultExtendedEntity?
    suspend fun getLikedBook(): List<BookEntity>
    suspend fun likeBook(book: BookEntity)
    suspend fun unlikeBook(book: BookEntity)
    suspend fun getLikedBookById(bookId: String): BookEntity?
    suspend fun getSearchBookById(bookId: String): BookEntity?
}


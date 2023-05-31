package com.illiouchine.mybook.data

import com.illiouchine.mybook.data.dataobject.SearchResultDataObject
import com.illiouchine.mybook.data.datasource.BookLocalDataSource
import com.illiouchine.mybook.data.datasource.BookRemoteDataSource
import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultExtendedEntity
import javax.inject.Inject

class BookDataMapper @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val bookLocalDataSource: BookLocalDataSource
): BookDataGateway {

    override suspend fun getBookByAuthorAndTitle(author: String, title: String): List<BookEntity> {
        val bookEntityList =
            bookRemoteDataSource.searchBook(author = author, title = title).toBookEntity()
        bookLocalDataSource.saveSearchResult(bookList = bookEntityList, author = author, title = title)
        return bookEntityList
    }

    override suspend fun getSearchResult(): SearchResultExtendedEntity {
        return bookLocalDataSource.getLastSearchResult().toEntity()
    }

    override suspend fun getLikedBook(): List<BookEntity> {
        return bookLocalDataSource.getLikedBooks()
    }

    private fun BookRemoteDataSource.BookResult.toBookEntity() : List<BookEntity> {
        return try {
            this.items.map {
                BookEntity(
                    title = it.volumeInfo.title,
                    author = it.volumeInfo.authors.first(),
                    description = it.volumeInfo.description,
                    imageUrl = it.volumeInfo.imageLinks?.thumbnail
                )
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}

private fun SearchResultDataObject.toEntity(): SearchResultExtendedEntity {
    return SearchResultExtendedEntity(
        list = this.bookList,
        author = this.author,
        title = this.title
    )
}

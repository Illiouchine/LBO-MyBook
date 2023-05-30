package com.illiouchine.mybook.data

import com.illiouchine.mybook.data.datasource.BookRemoteDataSource
import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import javax.inject.Inject

class BookDataMapper @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource
): BookDataGateway {

    override suspend fun getBookByAuthorAndTitle(author: String, title: String): List<BookEntity> {
        return bookRemoteDataSource.searchBook(author = author, title = title).toBookEntity()
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
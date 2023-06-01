package com.illiouchine.mybook.data

import com.illiouchine.mybook.data.dataobject.BookDataObject
import com.illiouchine.mybook.data.dataobject.LastAuthorAndTitle
import com.illiouchine.mybook.data.dataobject.LikedBookDataObject
import com.illiouchine.mybook.data.datasource.BookLocalDataSource
import com.illiouchine.mybook.data.datasource.BookRemoteDataSource
import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultExtendedEntity
import javax.inject.Inject

class BookDataMapper @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val bookLocalDataSource: BookLocalDataSource
) : BookDataGateway {

    override suspend fun getBookByAuthorAndTitle(author: String, title: String): List<BookEntity> {
        val bookEntityList = bookRemoteDataSource.searchBook(author = author, title = title)
        bookLocalDataSource.dropLastAuthorAndTitle()
        bookLocalDataSource.dropLastSearchResult()
        bookLocalDataSource.saveSearchResult(bookList = bookEntityList.toBookDataObject())
        bookLocalDataSource.saveLastAuthorAndTitle(LastAuthorAndTitle(author, title))
        return bookEntityList.toBookEntity()
    }

    override suspend fun getSearchResult(): SearchResultExtendedEntity? {
        val lastAuthorAndTitle = bookLocalDataSource.getLastAuthorAndTitle()
        val bookList = bookLocalDataSource.getLastSearchResult()
        return if (lastAuthorAndTitle != null && bookList.isNotEmpty()) {
            mapToEntity(bookList, lastAuthorAndTitle.author, lastAuthorAndTitle.title)
        } else {
            null
        }
    }

    override suspend fun getLikedBook(): List<BookEntity> {
        val bookList = bookLocalDataSource.getLikedBooks().likedBookDataObjectToEntity()
        return bookList
    }

    override suspend fun likeBook(book: BookEntity) {
        bookLocalDataSource.saveLikedBook(book.toDataObject())
    }

    override suspend fun unlikeBook(book: BookEntity) {
        bookLocalDataSource.removeLikedBook(book.toDataObject())
    }

    override suspend fun getLikedBookById(bookId: String): BookEntity? {
        return bookLocalDataSource.getLikedBookById(bookId)
    }

    override suspend fun getSearchBookById(bookId: String): BookEntity? {
        return bookLocalDataSource.getSearchById(bookId)
    }

}

private fun BookEntity.toDataObject(): LikedBookDataObject {
    return LikedBookDataObject(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        imageUrl = this.imageUrl
    )
}


private fun BookRemoteDataSource.BookResult.toBookEntity(): List<BookEntity> {
    return try {
        this.items.map {
            BookEntity(
                id = it.id,
                title = it.volumeInfo.title,
                author = it.volumeInfo.authors?.firstOrNull(),
                description = it.volumeInfo.description,
                imageUrl = it.volumeInfo.imageLinks?.thumbnail
            )
        }
    } catch (e: Exception) {
        emptyList()
    }
}

private fun List<LikedBookDataObject>.likedBookDataObjectToEntity(): List<BookEntity> {
    return this.map {
        BookEntity(
            id = it.id,
            title = it.title,
            author = it.author,
            description = it.description,
            imageUrl = it.imageUrl
        )
    }
}

private fun BookRemoteDataSource.BookResult.toBookDataObject(): List<BookDataObject> {
    return try {
        this.items.map {
            BookDataObject(
                id = it.id,
                title = it.volumeInfo.title,
                author = it.volumeInfo.authors?.firstOrNull(),
                description = it.volumeInfo.description,
                imageUrl = it.volumeInfo.imageLinks?.thumbnail
            )
        }
    } catch (e: Exception) {
        emptyList()
    }
}

private fun mapToEntity(
    bookList: List<BookDataObject>,
    author: String,
    title: String
): SearchResultExtendedEntity {
    return SearchResultExtendedEntity(
        list = bookList.toEntity(),
        author = author,
        title = title
    )
}

private fun List<BookDataObject>.toEntity(): List<BookEntity> {
    return this.map {
        BookEntity(
            id = it.id,
            title = it.title,
            author = it.author,
            description = it.description,
            imageUrl = it.imageUrl
        )
    }
}


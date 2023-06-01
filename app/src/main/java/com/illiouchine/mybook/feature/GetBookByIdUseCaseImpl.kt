package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import javax.inject.Inject

class GetBookByIdUseCaseImpl @Inject constructor(
    private val bookDataGateway: BookDataGateway
) : GetBookByIdUseCase {
    override suspend fun invoke(bookId: String): BookWithLikedEntity? {
        val likedBook : BookEntity? = bookDataGateway.getLikedBookById(bookId)
        if (likedBook != null){
            return likedBook.toLikedBook(true)
        }
        val searchBook: BookEntity? = bookDataGateway.getSearchBookById(bookId)
        return searchBook?.toLikedBook(false)
    }

    private fun BookEntity.toLikedBook(liked: Boolean): BookWithLikedEntity {
        return BookWithLikedEntity(
            id = this.id,
            title = this.title,
            author = this.author,
            description = this.description,
            imageUrl = this.imageUrl,
            liked = liked
        )
    }
}
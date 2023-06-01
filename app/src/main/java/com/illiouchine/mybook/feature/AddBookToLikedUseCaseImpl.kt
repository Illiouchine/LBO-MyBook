package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import javax.inject.Inject

class AddBookToLikedUseCaseImpl @Inject constructor(
    private val bookDataGateway: BookDataGateway
) : AddBookToLikedUseCase {
    override suspend operator fun invoke(book: BookEntity) {
        bookDataGateway.likeBook(book)
    }
}

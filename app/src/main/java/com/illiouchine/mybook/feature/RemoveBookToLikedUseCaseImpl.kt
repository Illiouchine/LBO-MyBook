package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import javax.inject.Inject

class RemoveBookToLikedUseCaseImpl @Inject constructor(
    private val bookDataGateway: BookDataGateway
) : RemoveBookToLikedUseCase {
    override suspend fun invoke(book: BookEntity) {
        bookDataGateway.unlikeBook(book)
    }
}
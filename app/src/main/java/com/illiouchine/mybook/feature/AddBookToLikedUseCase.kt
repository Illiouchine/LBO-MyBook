package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface AddBookToLikedUseCase {
    suspend operator fun invoke(book: BookEntity)
}
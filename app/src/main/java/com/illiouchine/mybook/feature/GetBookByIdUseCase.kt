package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface GetBookByIdUseCase {
    suspend operator fun invoke(bookId: String) : BookWithLikedEntity?
}

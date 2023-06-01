package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface GetMyLibUseCase{
    suspend operator fun invoke(): List<BookWithLikedEntity>
}

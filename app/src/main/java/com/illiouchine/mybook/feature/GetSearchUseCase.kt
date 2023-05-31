package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.SearchResultWithLikedBookEntity

interface GetSearchUseCase {

    suspend operator fun invoke(): SearchResultWithLikedBookEntity

}


package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultWithLikedBookEntity
import javax.inject.Inject

class GetSearchUseCaseImpl @Inject constructor(
    private val gateway: BookDataGateway
) : GetSearchUseCase {

    override suspend fun invoke(): SearchResultWithLikedBookEntity {

        val likedBook = gateway.getLikedBook()

        val searchResult = gateway.getSearchResult()

        val searchResultWithLiked = searchResult?.list?.map { searchedBook ->

            val liked = likedBook.any { searchedBook == it }

            BookWithLikedEntity(
                id = searchedBook.id,
                title = searchedBook.title,
                author = searchedBook.author,
                description = searchedBook.description,
                imageUrl = searchedBook.imageUrl,
                liked = liked
            )
        }

        return SearchResultWithLikedBookEntity(
            author = searchResult?.author ?: "",
            title = searchResult?.title ?: "",
            list = searchResultWithLiked ?: emptyList()
        )
    }
}
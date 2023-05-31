package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultWithLikedBookEntity
import javax.inject.Inject

class GetSearchUseCaseImpl @Inject constructor(
    private val gateway: BookDataGateway
) : GetSearchUseCase {

    override suspend fun invoke(): SearchResultWithLikedBookEntity {

        val searchResult = gateway.getSearchResult()
        val likedBook = gateway.getLikedBook()

        val searchResultWithLiked = searchResult?.list?.map { searchedBook ->
            BookWithLikedEntity(
                etag = searchedBook.etag,
                title = searchedBook.title,
                author = searchedBook.author,
                description = searchedBook.description,
                imageUrl = searchedBook.imageUrl,
                liked = likedBook.contains(
                    BookEntity(
                        etag = searchedBook.etag,
                        title = searchedBook.title,
                        author = searchedBook.author,
                        description = searchedBook.description,
                        imageUrl = searchedBook.imageUrl
                    )
                )
            )
        }

        return SearchResultWithLikedBookEntity(
            author = searchResult?.author ?: "",
            title = searchResult?.title ?: "",
            list = searchResultWithLiked ?: emptyList()
        )
    }
}
package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultEntity
import javax.inject.Inject

class PerformSearchUseCaseImpl @Inject constructor(
    private val bookDataGateway: BookDataGateway
): PerformSearchUseCase {

    override suspend fun invoke(author: String, title: String): SearchResultEntity {
        val result = bookDataGateway.getBookByAuthorAndTitle(author = author, title= title)
        return if (result.isEmpty()){
            SearchResultEntity.Error
        } else {
            SearchResultEntity.Result(
                books = result
            )
        }
    }
}



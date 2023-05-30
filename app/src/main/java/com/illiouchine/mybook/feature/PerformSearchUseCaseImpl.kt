package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import javax.inject.Inject

class PerformSearchUseCaseImpl @Inject constructor(
    private val bookDataGateway: BookDataGateway
): PerformSearchUseCase {

    override suspend fun invoke(author: String, title: String): PerformSearchUseCase.SearchResult {
        val result = bookDataGateway.getBookByAuthorAndTitle(author = author, title= title)
        return if (result.isEmpty()){
            PerformSearchUseCase.SearchResult.Error
        } else {
            PerformSearchUseCase.SearchResult.Result(
                books = result
            )
        }
    }
}



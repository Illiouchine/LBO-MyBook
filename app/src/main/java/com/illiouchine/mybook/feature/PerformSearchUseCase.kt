package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface PerformSearchUseCase{

    suspend operator fun invoke(author: String, title:String): SearchResult

    sealed class SearchResult{
        data class Result(val books : List<BookEntity>): SearchResult()
        object Error: SearchResult()
    }
}
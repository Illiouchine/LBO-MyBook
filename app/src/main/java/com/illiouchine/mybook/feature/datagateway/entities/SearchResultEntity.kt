package com.illiouchine.mybook.feature.datagateway.entities

sealed class SearchResultEntity{
    data class Result(val books : List<BookEntity>): SearchResultEntity()
    object Error: SearchResultEntity()
}
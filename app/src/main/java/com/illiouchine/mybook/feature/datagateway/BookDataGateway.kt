package com.illiouchine.mybook.feature.datagateway

import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface BookDataGateway {
    suspend fun getBookByAuthorAndTitle(author: String, title: String) : List<BookEntity>
}


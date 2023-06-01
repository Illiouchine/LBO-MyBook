package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import javax.inject.Inject

class GetMyLibUseCaseImpl @Inject constructor(
    private val gateway: BookDataGateway
) : GetMyLibUseCase {
    override suspend fun invoke(): List<BookWithLikedEntity> {
        return gateway.getLikedBook().toBookWithLikedEntity()
    }
}

private fun List<BookEntity>.toBookWithLikedEntity(): List<BookWithLikedEntity> {
    return this.map {
        BookWithLikedEntity(
            id = it.id,
            title = it.title,
            author = it.author,
            description = it.description,
            imageUrl = it.imageUrl,
            liked = true
        )
    }
}

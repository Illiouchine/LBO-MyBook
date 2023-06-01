package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.BookDataGateway
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultExtendedEntity
import com.illiouchine.mybook.feature.datagateway.entities.SearchResultWithLikedBookEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetSearchUseCaseImplTest {

    private val dataGateway: BookDataGateway = mock()

    private fun buildBookEntity(id: Int) = BookEntity(
        id = "id $id",
        title = "title $id",
        author = "author $id",
        description = "description $id",
        imageUrl = "imageUrl $id",
    )

    private fun buildBookWithLiked(id: Int, liked: Boolean = false) = BookWithLikedEntity(
        id = "id $id",
        title = "title $id",
        author = "author $id",
        description = "description $id",
        imageUrl = "imageUrl $id",
        liked = liked
    )

    private fun buildSearchResultExtendedEntity(list: List<BookEntity>) = SearchResultExtendedEntity(
        title = "title",
        author = "author",
        list = list
    )

    private fun expectedResult(list: List<BookWithLikedEntity>) = SearchResultWithLikedBookEntity(
        author = "author",
        title = "title",
        list = list
    )


    @Test
    fun `Should return searched book with no liked book`() = runBlocking {
        whenever(dataGateway.getLikedBook())
            .thenReturn(
                emptyList()
            )
        whenever(dataGateway.getSearchResult())
            .thenReturn(
                buildSearchResultExtendedEntity(
                    list = listOf(
                        buildBookEntity(1), buildBookEntity(2)
                    )
                )
            )

        val getSearchUseCase = GetSearchUseCaseImpl(dataGateway)

        val result = getSearchUseCase()
        assertEquals(expectedResult(listOf(buildBookWithLiked(1), buildBookWithLiked(2))), result)
    }

    @Test
    fun `Should return searched book with one liked book`() = runBlocking {
        whenever(dataGateway.getLikedBook())
            .thenReturn(
                listOf(buildBookEntity(2))
            )
        whenever(dataGateway.getSearchResult())
            .thenReturn(
                buildSearchResultExtendedEntity(
                    list = listOf(
                        buildBookEntity(1), buildBookEntity(2), buildBookEntity(3)
                    )
                )
            )

        val getSearchUseCase = GetSearchUseCaseImpl(dataGateway)

        val result = getSearchUseCase()
        assertEquals(
            expectedResult(
                listOf(
                    buildBookWithLiked(1),
                    buildBookWithLiked(2, true),
                    buildBookWithLiked(3)
                )
            ),
            result
        )
    }


    @Test
    fun `Shouldn't return any searched book`() = runBlocking {
        whenever(dataGateway.getLikedBook())
            .thenReturn(
                listOf(buildBookEntity(2))
            )
        whenever(dataGateway.getSearchResult())
            .thenReturn(
                buildSearchResultExtendedEntity(
                    list = emptyList()
                )
            )

        val getSearchUseCase = GetSearchUseCaseImpl(dataGateway)

        val result = getSearchUseCase()
        assertEquals(
            expectedResult(
                emptyList()
            ),
            result
        )
    }
}
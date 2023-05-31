package com.illiouchine.mybook.ui.screen.result

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface ResultContract {

    sealed class ResultIntent : UiIntent {
        data class BookTileClicked(val bookEntity: BookEntity) : ResultIntent()
        data class LikeClicked(val bookEntity: BookEntity) : ResultIntent()
    }

    sealed class ResultAction : UiAction {
        data class ShowBook(val bookEntity: BookEntity) : ResultAction()
        data class AddBookToLiked(val bookEntity: BookEntity) : ResultAction()
    }

    data class ResultState(
        val searchResult: SearchResult,
        override val event: UiEvent?,
    ) : UiState {
        sealed class SearchResult {
            data class Loadded(
                val bookList: List<BookWithLikedEntity>,
                val author: String,
                val title: String
            ) : SearchResult()

            object Loading : SearchResult()
        }

        sealed class ResultEvent : UiEvent {
            data class GoToBookDetail(
                val bookEntity: BookEntity
            ) : ResultEvent()
        }
    }

    sealed class ResultPartialState : UiPartialState {
        object Loading : ResultPartialState()
        data class Loadded(
            val bookList: List<BookWithLikedEntity>,
            val author: String,
            val title: String
        ) : ResultPartialState()
        data class GoToBookDetail(val bookEntity: BookEntity) : ResultPartialState()
        object ClearEvent : ResultPartialState()
    }
}
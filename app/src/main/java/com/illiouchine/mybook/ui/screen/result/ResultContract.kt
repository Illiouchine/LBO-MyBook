package com.illiouchine.mybook.ui.screen.result

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface ResultContract {

    sealed class ResultIntent : UiIntent {
        object EventHandled : ResultIntent()
        object Refresh : ResultIntent()

        data class BookTileClicked(val book: BookWithLikedEntity) : ResultIntent()
        data class LikeClicked(val book: BookWithLikedEntity) : ResultIntent()
    }

    sealed class ResultAction : UiAction {
        object ClearEvent : ResultAction()
        object Refresh : ResultAction()

        data class ShowBook(val book: BookWithLikedEntity) : ResultAction()
        data class AddBookToLiked(val book: BookWithLikedEntity) : ResultAction()
        data class RemoveBookToLiked(val book: BookWithLikedEntity) : ResultAction()
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
                val book: BookWithLikedEntity
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
        data class GoToBookDetail(val book: BookWithLikedEntity) : ResultPartialState()
        object ClearEvent : ResultPartialState()
    }
}
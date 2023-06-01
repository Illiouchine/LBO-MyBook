package com.illiouchine.mybook.ui.screen.mylib

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface MyLibContract {


    sealed class MyLibIntent : UiIntent {
        object EventHandled : MyLibIntent()

        data class BookTileClicked(val book: BookWithLikedEntity) : MyLibIntent()
        data class LikeClicked(val book: BookWithLikedEntity) : MyLibIntent()
    }

    sealed class MyLibAction : UiAction {
        object ClearEvent : MyLibAction()

        data class ShowBook(val book: BookWithLikedEntity) : MyLibAction()
        data class RemoveBookToLiked(val book: BookWithLikedEntity) : MyLibAction()
    }

    data class MyLibState(
        val state: MyLibBookState,
        override val event: UiEvent?,
    ) : UiState {

        sealed class MyLibBookState {
            data class Loadded(val bookList: List<BookWithLikedEntity>) : MyLibBookState()
            object Loading : MyLibBookState()
        }

        sealed class MyLibEvent : UiEvent {
            data class GoToBookDetail(
                val book: BookWithLikedEntity
            ) : MyLibEvent()
        }
    }

    sealed class MyLibPartialState : UiPartialState {
        object Loading : MyLibPartialState()
        data class Loadded(val bookList: List<BookWithLikedEntity>, ) : MyLibPartialState()
        data class GoToBookDetail(val book: BookWithLikedEntity) : MyLibPartialState()
        object ClearEvent : MyLibPartialState()
    }
}
package com.illiouchine.mybook.ui.screen.mylib

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface MyLibContract {


    sealed class MyLibIntent : UiIntent {
        data class BookTileClicked(val bookEntity: BookEntity) : MyLibIntent()
        data class LikeClicked(val book: BookWithLikedEntity) : MyLibIntent()
    }

    sealed class MyLibAction : UiAction {
        data class ShowBook(val bookEntity: BookEntity) : MyLibAction()
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
                val bookEntity: BookEntity
            ) : MyLibEvent()
        }
    }

    sealed class MyLibPartialState : UiPartialState {
        object Loading : MyLibPartialState()
        data class Loadded(val bookList: List<BookWithLikedEntity>, ) : MyLibPartialState()
        data class GoToBookDetail(val bookEntity: BookEntity) : MyLibPartialState()
        object ClearEvent : MyLibPartialState()
    }
}
package com.illiouchine.mybook.ui.screen.detail

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity

interface DetailContract {

    sealed class DetailIntent : UiIntent {
        object EventHandled : DetailIntent()
        data class LikeClicked(val book: BookWithLikedEntity) : DetailIntent()
    }

    sealed class DetailAction : UiAction {
        object ClearEvent : DetailAction()
        data class RemoveBookToLiked(val book: BookWithLikedEntity) : DetailAction()
        data class AddBookToLiked(val book: BookWithLikedEntity) : DetailAction()
    }

    data class DetailState(
        val state: DetailBookState,
        override val event: UiEvent?,
    ) : UiState {

        sealed class DetailBookState {
            data class Loadded(val book: BookWithLikedEntity) : DetailBookState()
            object Loading : DetailBookState()
        }

        sealed class DetailEvent : UiEvent {}
    }

    sealed class DetailPartialState : UiPartialState {
        object Loading : DetailPartialState()
        data class Loadded(val book: BookWithLikedEntity ) : DetailPartialState()
        object ClearEvent : DetailPartialState()
    }
}
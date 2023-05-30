package com.illiouchine.mybook.ui.screen

import com.illiouchine.mvi.core.*
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity

interface SearchContract {

    sealed class SearchIntent : UiIntent {
        data class Search(val author : String, val title: String) : SearchIntent()
        object MyLibraryClicked : SearchIntent()
        object EventHandled: SearchIntent()
    }

    sealed class SearchAction : UiAction {
        data class PerformSearch(val author : String, val title: String) : SearchAction()
        object GoToMyLibrary : SearchAction()
        object ClearEvent : SearchAction()
    }

    data class SearchState(
        val searchProgress: SearchProgress,
        override val event: UiEvent?,
    ) : UiState {
        sealed class SearchProgress {
            object Idle : SearchProgress()
            object Loading : SearchProgress()
        }
        sealed class SearchEvent : UiEvent {
            object GoToMyLibrary: SearchEvent()
            data class GoToSearchResult(val bookList: List<BookEntity>): SearchEvent()
        }
    }

    sealed class SearchPartialState : UiPartialState {
        object Loading : SearchPartialState()
        object Idle : SearchPartialState()
        data class GoToBookList(val bookList: List<BookEntity>) : SearchPartialState()
        object GoToMyLibrary : SearchPartialState()
        object ClearEvent : SearchPartialState()
    }
}
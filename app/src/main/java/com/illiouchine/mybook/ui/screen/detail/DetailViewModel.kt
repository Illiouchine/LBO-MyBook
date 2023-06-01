package com.illiouchine.mybook.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.illiouchine.mvi.core.MviViewModel
import com.illiouchine.mvi.core.Reducer
import com.illiouchine.mybook.feature.AddBookToLikedUseCase
import com.illiouchine.mybook.feature.GetBookByIdUseCase
import com.illiouchine.mybook.feature.RemoveBookToLikedUseCase
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.illiouchine.mybook.ui.screen.detail.DetailContract.DetailAction as Action
import com.illiouchine.mybook.ui.screen.detail.DetailContract.DetailIntent as Intent
import com.illiouchine.mybook.ui.screen.detail.DetailContract.DetailPartialState as PartialState
import com.illiouchine.mybook.ui.screen.detail.DetailContract.DetailState as State

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val likeUseCase: AddBookToLikedUseCase,
    private val unlikeUseCase: RemoveBookToLikedUseCase
) : MviViewModel<Intent, Action, PartialState, State>() {

    private val bookId: String = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            loadBookDetail()
        }
    }

    private suspend fun loadBookDetail() {
        getBookByIdUseCase(bookId)?.let {
            setPartialState {
                PartialState.Loadded(
                    book = it
                )
            }
        }
    }

    override fun createInitialState(): State {
        return State(
            state = State.DetailBookState.Loading,
            event = null
        )
    }

    override fun createReducer(): Reducer<State, PartialState> {
        return object : Reducer<State, PartialState>() {
            override fun reduce(
                currentState: DetailContract.DetailState,
                partialState: DetailContract.DetailPartialState
            ): DetailContract.DetailState {
                return when (partialState) {
                    DetailContract.DetailPartialState.ClearEvent -> {
                        currentState.copy(
                            event = null
                        )
                    }
                    is DetailContract.DetailPartialState.Loadded -> {
                        currentState.copy(
                            state = State.DetailBookState.Loadded(
                                book = partialState.book
                            )
                        )
                    }
                    DetailContract.DetailPartialState.Loading -> {
                        currentState.copy(
                            state = State.DetailBookState.Loading
                        )
                    }
                }
            }

        }
    }

    override fun handleUserIntent(intent: Intent): Action {
        return when (intent) {
            Intent.EventHandled -> {
                Action.ClearEvent
            }
            is Intent.LikeClicked -> {
                if (intent.book.liked) {
                    Action.RemoveBookToLiked(intent.book)
                } else {
                    Action.AddBookToLiked(intent.book)
                }
            }
        }
    }

    override suspend fun handleAction(action: DetailContract.DetailAction) {
        when (action) {
            is Action.AddBookToLiked -> {
                likeUseCase(action.book.toBookEntity())
                loadBookDetail()
            }
            Action.ClearEvent -> {
                setPartialState { PartialState.ClearEvent }
            }
            is Action.RemoveBookToLiked -> {
                unlikeUseCase(action.book.toBookEntity())
                loadBookDetail()
            }
        }
    }
}

// TODO refactor with ResultViewModel method
private fun BookWithLikedEntity.toBookEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        imageUrl = this.imageUrl
    )
}


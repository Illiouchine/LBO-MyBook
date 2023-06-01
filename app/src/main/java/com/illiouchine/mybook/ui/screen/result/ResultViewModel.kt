package com.illiouchine.mybook.ui.screen.result

import androidx.lifecycle.viewModelScope
import com.illiouchine.mvi.core.MviViewModel
import com.illiouchine.mvi.core.Reducer
import com.illiouchine.mybook.feature.AddBookToLikedUseCase
import com.illiouchine.mybook.feature.GetSearchUseCase
import com.illiouchine.mybook.feature.RemoveBookToLikedUseCase
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.illiouchine.mybook.ui.screen.result.ResultContract.ResultAction as Action
import com.illiouchine.mybook.ui.screen.result.ResultContract.ResultIntent as Intent
import com.illiouchine.mybook.ui.screen.result.ResultContract.ResultPartialState as PartialState
import com.illiouchine.mybook.ui.screen.result.ResultContract.ResultState as State

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val addBookToLikedUseCase: AddBookToLikedUseCase,
    private val removeBookToLikedUseCase: RemoveBookToLikedUseCase,
) : MviViewModel<Intent, Action, PartialState, State>() {

    init {
        loadBookList()
    }


    override fun createInitialState(): ResultContract.ResultState = ResultContract.ResultState(
        searchResult = ResultContract.ResultState.SearchResult.Loading,
        event = null
    )

    override fun createReducer(): Reducer<ResultContract.ResultState, ResultContract.ResultPartialState> {
        return object : Reducer<State, PartialState>() {
            override fun reduce(
                currentState: ResultContract.ResultState,
                partialState: ResultContract.ResultPartialState
            ): ResultContract.ResultState {
                return when (partialState) {
                    ResultContract.ResultPartialState.ClearEvent -> {
                        currentState.copy(event = null)
                    }
                    is ResultContract.ResultPartialState.GoToBookDetail -> {
                        currentState.copy(
                            event = ResultContract.ResultState.ResultEvent.GoToBookDetail(
                                bookEntity = partialState.bookEntity
                            )
                        )
                    }
                    is ResultContract.ResultPartialState.Loadded -> {
                        currentState.copy(
                            searchResult = ResultContract.ResultState.SearchResult.Loadded(
                                bookList = partialState.bookList,
                                author = partialState.author,
                                title = partialState.title
                            )
                        )
                    }
                    ResultContract.ResultPartialState.Loading -> {
                        currentState.copy(
                            searchResult = ResultContract.ResultState.SearchResult.Loading
                        )
                    }
                }
            }

        }
    }

    override fun handleUserIntent(intent: ResultContract.ResultIntent): ResultContract.ResultAction {
        return when (intent) {
            is ResultContract.ResultIntent.BookTileClicked -> {
                Action.ShowBook(
                    bookEntity = intent.bookEntity
                )
            }
            is ResultContract.ResultIntent.LikeClicked -> {
                if (intent.book.liked) {
                    Action.RemoveBookToLiked(
                        book = intent.book
                    )
                } else {
                    Action.AddBookToLiked(
                        book = intent.book
                    )
                }
            }
        }
    }

    override suspend fun handleAction(action: ResultContract.ResultAction) {
        when (action) {
            is ResultContract.ResultAction.AddBookToLiked -> {
                viewModelScope.launch {
                    addBookToLikedUseCase(action.book.toBookEntity())
                    loadBookList()
                }
            }
            is ResultContract.ResultAction.ShowBook -> {
                setPartialState { PartialState.GoToBookDetail(bookEntity = action.bookEntity) }
            }
            is ResultContract.ResultAction.RemoveBookToLiked -> {
                viewModelScope.launch {
                    removeBookToLikedUseCase(action.book.toBookEntity())
                    loadBookList()
                }
            }
        }
    }

    private fun loadBookList() {
        viewModelScope.launch {
            val result = getSearchUseCase()
            setPartialState {
                PartialState.Loadded(
                    bookList = result.list,
                    author = result.author,
                    title = result.title
                )
            }
        }
    }
}

private fun BookWithLikedEntity.toBookEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        imageUrl = this.imageUrl
    )
}

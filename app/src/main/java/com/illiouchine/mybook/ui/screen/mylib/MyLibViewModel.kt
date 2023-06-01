package com.illiouchine.mybook.ui.screen.mylib

import androidx.lifecycle.viewModelScope
import com.illiouchine.mvi.core.MviViewModel
import com.illiouchine.mvi.core.Reducer
import com.illiouchine.mybook.feature.GetMyLibUseCase
import com.illiouchine.mybook.feature.RemoveBookToLikedUseCase
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLibViewModel @Inject constructor(
    private val getMyLibUseCase: GetMyLibUseCase,
    private val removeBookToLikedUseCase: RemoveBookToLikedUseCase,
) : MviViewModel<MyLibContract.MyLibIntent, MyLibContract.MyLibAction, MyLibContract.MyLibPartialState, MyLibContract.MyLibState>() {

    init {
        loadMyLib()
    }

    override fun createInitialState(): MyLibContract.MyLibState = MyLibContract.MyLibState(
        state = MyLibContract.MyLibState.MyLibBookState.Loading,
        event = null
    )

    override fun createReducer(): Reducer<MyLibContract.MyLibState, MyLibContract.MyLibPartialState> {
        return object : Reducer<MyLibContract.MyLibState, MyLibContract.MyLibPartialState>() {
            override fun reduce(
                currentState: MyLibContract.MyLibState,
                partialState: MyLibContract.MyLibPartialState
            ): MyLibContract.MyLibState {
                return when (partialState) {
                    MyLibContract.MyLibPartialState.ClearEvent -> {
                        currentState.copy(event = null)
                    }
                    is MyLibContract.MyLibPartialState.GoToBookDetail -> {
                        currentState.copy(
                            event = MyLibContract.MyLibState.MyLibEvent.GoToBookDetail(
                                book = partialState.book
                            )
                        )
                    }
                    is MyLibContract.MyLibPartialState.Loadded -> {
                        currentState.copy(
                            state = MyLibContract.MyLibState.MyLibBookState.Loadded(
                                bookList = partialState.bookList,
                            )
                        )
                    }
                    MyLibContract.MyLibPartialState.Loading -> {
                        currentState.copy(
                            state = MyLibContract.MyLibState.MyLibBookState.Loading
                        )
                    }
                }
            }

        }
    }

    override fun handleUserIntent(intent: MyLibContract.MyLibIntent): MyLibContract.MyLibAction {
        return when (intent) {
            is MyLibContract.MyLibIntent.BookTileClicked -> {
                MyLibContract.MyLibAction.ShowBook(
                    book = intent.book
                )
            }
            is MyLibContract.MyLibIntent.LikeClicked -> {
                    MyLibContract.MyLibAction.RemoveBookToLiked(
                        book = intent.book
                    )
            }
            MyLibContract.MyLibIntent.EventHandled -> {
                MyLibContract.MyLibAction.ClearEvent
            }
        }
    }

    override suspend fun handleAction(action: MyLibContract.MyLibAction) {
        when (action) {
            is MyLibContract.MyLibAction.ShowBook -> {
                setPartialState { MyLibContract.MyLibPartialState.GoToBookDetail(book = action.book) }
            }
            is MyLibContract.MyLibAction.RemoveBookToLiked -> {
                viewModelScope.launch {
                    removeBookToLikedUseCase(action.book.toBookEntity())
                    loadMyLib()
                }
            }
            MyLibContract.MyLibAction.ClearEvent -> {
                setPartialState { MyLibContract.MyLibPartialState.ClearEvent }
            }
        }
    }

    private fun loadMyLib() {
        viewModelScope.launch {
            val myBook = getMyLibUseCase()
            setPartialState {
                MyLibContract.MyLibPartialState.Loadded(
                    bookList = myBook,
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

package com.illiouchine.mybook.ui.screen

import androidx.lifecycle.viewModelScope
import com.illiouchine.mvi.core.MviViewModel
import com.illiouchine.mvi.core.Reducer
import com.illiouchine.mybook.feature.PerformSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.illiouchine.mybook.ui.screen.SearchContract.SearchAction as Action
import com.illiouchine.mybook.ui.screen.SearchContract.SearchIntent as Intent
import com.illiouchine.mybook.ui.screen.SearchContract.SearchPartialState as PartialState
import com.illiouchine.mybook.ui.screen.SearchContract.SearchState as State

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val performSearchUseCase: PerformSearchUseCase
) : MviViewModel<Intent, Action, PartialState, State>() {

    override fun createInitialState(): State =
        State(searchProgress = State.SearchProgress.Idle, event = null)

    override fun handleUserIntent(intent: Intent): Action {
        return when (intent){
            Intent.MyLibraryClicked -> Action.GoToMyLibrary
            is Intent.Search -> Action.PerformSearch(author = intent.author, title = intent.title)
            Intent.EventHandled -> Action.ClearEvent
        }
    }

    override fun createReducer(): Reducer<SearchContract.SearchState, SearchContract.SearchPartialState> {
        return object : Reducer<State, PartialState>() {
            override fun reduce(
                currentState: SearchContract.SearchState,
                partialState: SearchContract.SearchPartialState
            ): SearchContract.SearchState {
                return when (partialState){
                    is SearchContract.SearchPartialState.GoToBookList -> {
                        currentState.copy(
                            searchProgress = State.SearchProgress.Idle,
                            event = State.SearchEvent.GoToSearchResult(bookList = partialState.bookList)
                        )
                    }
                    SearchContract.SearchPartialState.GoToMyLibrary -> {
                        currentState.copy(
                            event = State.SearchEvent.GoToMyLibrary
                        )
                    }
                    SearchContract.SearchPartialState.Idle -> {
                        currentState.copy(
                            searchProgress = State.SearchProgress.Idle
                        )
                    }
                    SearchContract.SearchPartialState.Loading -> {
                        currentState.copy(
                            searchProgress = State.SearchProgress.Loading
                        )
                    }
                    SearchContract.SearchPartialState.ClearEvent -> {
                        currentState.copy(
                            event = null
                        )
                    }
                }
            }
        }
    }


    override suspend fun handleAction(action: SearchContract.SearchAction) {
        when(action){
            SearchContract.SearchAction.GoToMyLibrary -> {
                setPartialState {
                    PartialState.GoToMyLibrary
                }
            }
            is SearchContract.SearchAction.PerformSearch -> {
                setPartialState { PartialState.Loading }
                viewModelScope.launch {
                    when(val result = performSearchUseCase(author = action.author,title = action.title)){
                        PerformSearchUseCase.SearchResult.Error -> {
                            setPartialState { PartialState.Idle }
                            // TODO Manage feedback on error
                        }
                        is PerformSearchUseCase.SearchResult.Result -> {
                            setPartialState { PartialState.GoToBookList(bookList = result.books) }
                        }
                    }
                }
            }
            Action.ClearEvent -> {
                setPartialState { PartialState.ClearEvent }
            }
        }
    }
}
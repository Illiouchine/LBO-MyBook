package com.illiouchine.mybook.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.ui.screen.search.SearchContract.SearchState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchProgressState : State = State(searchProgress = State.SearchProgress.Idle, event = null),
    onSearchClick: (author: String, title: String) -> Unit = { _, _ -> },
    onMyLibraryClick: () -> Unit = {},
    onEventHandled: () -> Unit = {},
    onNavigateToMyLibrary: () -> Unit = {},
    onNavigateToSearchResult: (bookList: List<BookEntity>) -> Unit = {}
) {
    when (searchProgressState.event){
        is State.SearchEvent.GoToMyLibrary -> {
            onEventHandled()
            onNavigateToMyLibrary()
        }
        is State.SearchEvent.GoToSearchResult -> {
            onEventHandled()
            onNavigateToSearchResult(searchProgressState.event.bookList)
        }
    }

    var author by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Book store")
        TextField(
            value = author,
            label = { Text("Author") },
            onValueChange = { newValue ->
                author = newValue
            }
        )
        TextField(
            value = title,
            label = { Text("Title") },
            onValueChange = { newTitle ->
                title = newTitle
            }
        )
        when(searchProgressState.searchProgress){
            SearchContract.SearchState.SearchProgress.Idle -> {
                Button(
                    onClick = { onSearchClick(author, title) }
                ) {
                    Text(text = "search")
                }
            }
            SearchContract.SearchState.SearchProgress.Loading -> {
                CircularProgressIndicator()
            }
        }

        Button(
            onClick = { onMyLibraryClick() }
        ) {
            Text("My Library")
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
package com.illiouchine.mybook.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.illiouchine.mybook.feature.datagateway.entities.BookEntity
import com.illiouchine.mybook.ui.screen.search.SearchContract.SearchState as State

@Composable
fun SearchScreen(
    searchProgressState: State = State(searchProgress = State.SearchProgress.Idle, event = null),
    onSearchClick: (author: String, title: String) -> Unit = { _, _ -> },
    onMyLibraryClick: () -> Unit = {},
    onEventHandled: () -> Unit = {},
    onNavigateToMyLibrary: () -> Unit = {},
    onNavigateToSearchResult: (bookList: List<BookEntity>) -> Unit = {}
) {
    when (searchProgressState.event) {
        is State.SearchEvent.GoToMyLibrary -> {
            onEventHandled()
            onNavigateToMyLibrary()
        }
        is State.SearchEvent.GoToSearchResult -> {
            onEventHandled()
            onNavigateToSearchResult(searchProgressState.event.bookList)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Book store")

        when (searchProgressState.searchProgress) {
            SearchContract.SearchState.SearchProgress.Idle -> {
                SearchForm(
                    lastSearchAuthor = "",
                    lastSearchTitle = "",
                    onSearchClick = { author, title ->
                        onSearchClick(author, title)
                    }
                )
            }
            is SearchContract.SearchState.SearchProgress.IdleWithPreviousSearch -> {
                SearchForm(
                    lastSearchAuthor = searchProgressState.searchProgress.author,
                    lastSearchTitle = searchProgressState.searchProgress.title,
                    errorMessage = searchProgressState.searchProgress.error,
                    onSearchClick = { author, title ->
                        onSearchClick(author, title)
                    }
                )
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

@Composable
fun SearchForm(
    lastSearchAuthor: String,
    lastSearchTitle: String,
    errorMessage: String? = null,
    onSearchClick: (author: String, title: String) -> Unit = { _, _ -> },
) {
    var author by remember { mutableStateOf(TextFieldValue(lastSearchAuthor)) }
    var title by remember { mutableStateOf(TextFieldValue(lastSearchTitle)) }

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

    errorMessage?.let {
        Text(text = it, color = Color.Red)
    }

    Button(
        enabled = (author.text.isNotEmpty() && title.text.isNotEmpty()),
        onClick = { onSearchClick(author.text, title.text) },
    ) {
        Text(text = "search")
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
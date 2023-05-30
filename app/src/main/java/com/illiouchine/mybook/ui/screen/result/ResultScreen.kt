package com.illiouchine.mybook.ui.screen.result

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultScreen(
    resultState: ResultContract.ResultState = ResultContract.ResultState(
        searchResult = ResultContract.ResultState.SearchResult.Loading,
        event = null
    ),
    onEventHandled: () -> Unit = {},
    onNavigateToBookDetail: () -> Unit = {},
) {
    when (resultState.event) {
        ResultContract.ResultState.ResultEvent.GoToBookDetail -> {
            onEventHandled()
            onNavigateToBookDetail()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (resultState.searchResult) {
            is ResultContract.ResultState.SearchResult.Loadded -> {
                items(resultState.searchResult.bookList) { book ->
                    Row {
                        Text(text = book.title)
                        Text(text = book.author)
                    }
                }
            }
            ResultContract.ResultState.SearchResult.Loading -> {
                item {
                    Text("Loading")
                }
            }
        }
    }
}
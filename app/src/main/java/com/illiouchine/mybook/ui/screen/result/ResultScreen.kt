package com.illiouchine.mybook.ui.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.ui.composable.BookVignette

@Composable
fun ResultScreen(
    resultState: ResultContract.ResultState = ResultContract.ResultState(
        searchResult = ResultContract.ResultState.SearchResult.Loading,
        event = null
    ),
    onEventHandled: () -> Unit = {},
    onNavigateToBookDetail: (book: BookWithLikedEntity) -> Unit = {},
    onLikeClicked: (book: BookWithLikedEntity) -> Unit = {},
    onBookClicked: (book: BookWithLikedEntity) -> Unit = {},
) {
    when (resultState.event) {
        is ResultContract.ResultState.ResultEvent.GoToBookDetail -> {
            onEventHandled()
            onNavigateToBookDetail(resultState.event.book)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when (resultState.searchResult) {
            is ResultContract.ResultState.SearchResult.Loadded -> {
                items(resultState.searchResult.bookList) { book ->
                    BookVignette(
                        book = book,
                        onBookClicked = { clickedBook ->
                            onBookClicked(clickedBook)
                        },
                        onLikeClicked = { likedBook ->
                            onLikeClicked(likedBook)
                        }
                    )
                }
            }
            ResultContract.ResultState.SearchResult.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

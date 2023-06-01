package com.illiouchine.mybook.ui.screen.detail

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.ui.composable.BookVignette

@Composable
fun DetailScreen(
    state: DetailContract.DetailState,
    onEventHandled: () -> Unit = {},
    onLikeClicked: (book: BookWithLikedEntity) -> Unit = {}
) {
    when (state.state) {
        is DetailContract.DetailState.DetailBookState.Loadded -> {
            val book = state.state.book
            BookVignette(book = book,
                onBookClicked = { },
                onLikeClicked = { onLikeClicked(it) })
        }
        DetailContract.DetailState.DetailBookState.Loading -> {
            CircularProgressIndicator()
        }
    }
}
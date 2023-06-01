package com.illiouchine.mybook.ui.screen.mylib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.ui.composable.BookVignette

@Composable
fun MyLibScreen(
    myLibState: MyLibContract.MyLibState = MyLibContract.MyLibState(
        state = MyLibContract.MyLibState.MyLibBookState.Loading,
        event = null
    ),
    onEventHandled: () -> Unit = {},
    onNavigateToBookDetail: (book: BookWithLikedEntity) -> Unit = {},
    onLikeClicked: (book: BookWithLikedEntity) -> Unit = {},
    onBookClicked: (book: BookWithLikedEntity) -> Unit = {}
) {
    when (myLibState.event) {
        is MyLibContract.MyLibState.MyLibEvent.GoToBookDetail -> {
            onEventHandled()
            onNavigateToBookDetail(myLibState.event.book)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        when (myLibState.state) {
            is MyLibContract.MyLibState.MyLibBookState.Loadded -> {
                items(myLibState.state.bookList) { book ->
                    BookVignette(
                        book = book,
                        onBookClicked = { clickedBook->
                            onBookClicked(clickedBook)
                        },
                        onLikeClicked = { likedBook ->
                            onLikeClicked(likedBook)
                        }
                    )
                }
            }
            MyLibContract.MyLibState.MyLibBookState.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

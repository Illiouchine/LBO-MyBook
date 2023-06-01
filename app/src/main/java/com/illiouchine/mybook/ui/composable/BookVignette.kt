package com.illiouchine.mybook.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity


@Composable
fun BookVignette(
    book: BookWithLikedEntity,
    onBookClicked: (book:BookWithLikedEntity) -> Unit = {},
    onLikeClicked: (book: BookWithLikedEntity) -> Unit = {},
) {
    Row {
        Card(
            modifier = Modifier.clickable {
                onBookClicked(book)
            }
        ) {
            Box{
                Column {
                    book.imageUrl?.let {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.replace("http", "https")) // Todo Find a better placement to do the https thing
                                .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.Yellow),
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ){
                        Text(text = book.title)
                        book.author?.let{
                            Text(text = it)
                        }
                        book.description?.let{
                            Text(text = it)
                        }
                    }
                }

                LikeIcon(
                    modifier = Modifier.align(Alignment.TopEnd),
                    liked = book.liked,
                    onLikeClicked = {
                        onLikeClicked(book)
                    }
                )
            }
        }
    }
}


@Composable
fun LikeIcon(
    modifier: Modifier = Modifier,
    liked: Boolean,
    onLikeClicked : () -> Unit = {}
) {
    if ( liked ){
        Icon(
            modifier = modifier
                .padding(8.dp)
                .size(44.dp)
                .clickable { onLikeClicked() },
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Remove to Favorite",
            tint = Color.Red
        )
    }else {
        Icon(
            modifier = modifier
                .padding(8.dp)
                .size(44.dp)
                .clickable { onLikeClicked() },
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Add to Favorite"
        )
    }
}

@Preview
@Composable
fun PrevBookVignette() {
    BookVignette(book = BookWithLikedEntity(
        id = "id",
        title = "Title",
        author = "Author",
        description = "Une super longue description",
        imageUrl = "ImageUrl",
        liked = false
    ))
}


@Preview
@Composable
fun PrevBookVignette2() {
    BookVignette(book = BookWithLikedEntity(
        id = "id",
        title = "Title",
        author = "Author",
        description = "Une super longue description",
        imageUrl = "ImageUrl",
        liked = true
    ))
}
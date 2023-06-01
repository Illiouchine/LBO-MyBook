package com.illiouchine.mybook.ui.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity


@Composable
fun BookVignette(
    book: BookWithLikedEntity,
    onBookClicked: (book: BookWithLikedEntity) -> Unit = {},
    onLikeClicked: (book: BookWithLikedEntity) -> Unit = {},
) {

    var expandedState by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clickable {
                onBookClicked(book)
            }
            .animateContentSize(
                animationSpec = TweenSpec(300)
            )
    ) {
        Box {
            Column {
                book.imageUrl?.let {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                it.replace(
                                    "http",
                                    "https"
                                )
                            ) // Todo Find a better placement to do the https thing
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
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    book.author?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    book.description?.let {
                        if (expandedState) {
                            Text(
                                text = it,
                            )
                        } else {
                            Text(
                                text = it,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            modifier = Modifier.padding(4.dp),
                            onClick = { expandedState = !expandedState },
                        ) {
                            if (expandedState) {
                                Text(text = "see less")
                            } else {
                                Text(text = "see more")
                            }
                        }
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


@Composable
fun LikeIcon(
    modifier: Modifier = Modifier,
    liked: Boolean,
    onLikeClicked: () -> Unit = {}
) {
    if (liked) {
        Icon(
            modifier = modifier
                .padding(8.dp)
                .size(44.dp)
                .clickable { onLikeClicked() },
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Remove to Favorite",
            tint = Color.Red
        )
    } else {
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
    BookVignette(
        book = BookWithLikedEntity(
            id = "id",
            title = "Title",
            author = "Author",
            description = "Oscillation within a space time matrix, with respect to hyperbolic sub-resonant variations, combined with total holistic resonant emanations calling forth spiritual entrainment between sentient beings.\n" +
                    "\n" +
                    "This technology combines quantum dialectic projections, bonded on a quantum level with a higher order harmonic series calling forth a higher order contextual latency.\n" +
                    "\n" +
                    "Focusing on a single point in space-time can lead to an understanding of stabilized vibrational resonance, bonded on a quantum level with geodesic lattice structures in order to generate a psychic connection to the reality plane.\n" +
                    "\n" +
                    "The ability to fold space within the reality envelope facilitates the ability to make use of holographic temporal resonance, interwoven on a molecular level with isotropic transfer functions manifesting clear connection to the Akashic Records.",
            imageUrl = "ImageUrl",
            liked = false
        )
    )
}


@Preview
@Composable
fun PrevBookVignette2() {
    BookVignette(
        book = BookWithLikedEntity(
            id = "id",
            title = "Title",
            author = "Author",
            description = "Oscillation within a space time matrix, with respect to hyperbolic sub-resonant variations, combined with total holistic resonant emanations calling forth spiritual entrainment between sentient beings.\n" +
                    "\n" +
                    "This technology combines quantum dialectic projections, bonded on a quantum level with a higher order harmonic series calling forth a higher order contextual latency.\n" +
                    "\n" +
                    "Focusing on a single point in space-time can lead to an understanding of stabilized vibrational resonance, bonded on a quantum level with geodesic lattice structures in order to generate a psychic connection to the reality plane.\n" +
                    "\n" +
                    "The ability to fold space within the reality envelope facilitates the ability to make use of holographic temporal resonance, interwoven on a molecular level with isotropic transfer functions manifesting clear connection to the Akashic Records.",
            imageUrl = "ImageUrl",
            liked = true
        )
    )
}
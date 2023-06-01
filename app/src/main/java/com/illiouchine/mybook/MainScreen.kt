package com.illiouchine.mybook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.illiouchine.mybook.feature.datagateway.entities.BookWithLikedEntity
import com.illiouchine.mybook.ui.screen.detail.DetailContract
import com.illiouchine.mybook.ui.screen.detail.DetailScreen
import com.illiouchine.mybook.ui.screen.detail.DetailViewModel
import com.illiouchine.mybook.ui.screen.mylib.MyLibContract.MyLibIntent
import com.illiouchine.mybook.ui.screen.mylib.MyLibScreen
import com.illiouchine.mybook.ui.screen.mylib.MyLibViewModel
import com.illiouchine.mybook.ui.screen.result.ResultContract.ResultIntent
import com.illiouchine.mybook.ui.screen.result.ResultScreen
import com.illiouchine.mybook.ui.screen.result.ResultViewModel
import com.illiouchine.mybook.ui.screen.search.SearchContract.SearchIntent
import com.illiouchine.mybook.ui.screen.search.SearchScreen
import com.illiouchine.mybook.ui.screen.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EBook") }
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "search") {
                composable("search") {
                    val searchViewModel = hiltViewModel<SearchViewModel>()
                    val searchState by searchViewModel.uiState.collectAsState()
                    SearchScreen(
                        searchProgressState = searchState,
                        onSearchClick = { author, title ->
                            searchViewModel.dispatchIntent(
                                SearchIntent.Search(author = author, title = title)
                            )
                        },
                        onMyLibraryClick = {
                            searchViewModel.dispatchIntent(
                                SearchIntent.MyLibraryClicked
                            )
                        },
                        onEventHandled = {
                            searchViewModel.dispatchIntent(SearchIntent.EventHandled)
                        },
                        onNavigateToMyLibrary = {
                            navController.navigate("myLib")
                        },
                        onNavigateToSearchResult = {
                            navController.navigate("searchResult")
                        }
                    )
                }
                composable("myLib"){
                    val myLibViewModel = hiltViewModel<MyLibViewModel>()
                    val myLibState by myLibViewModel.uiState.collectAsState()
                    MyLibScreen(
                        myLibState = myLibState,
                        onEventHandled = { myLibViewModel.dispatchIntent(MyLibIntent.EventHandled) },
                        onNavigateToBookDetail = { book -> navController.navigate("book/${book.id}") },
                        onLikeClicked = { book -> myLibViewModel.dispatchIntent(MyLibIntent.LikeClicked(book))},
                        onBookClicked = { book -> myLibViewModel.dispatchIntent(MyLibIntent.BookTileClicked(book)) },
                        onRefresh = { myLibViewModel.dispatchIntent(MyLibIntent.Refresh) }
                    )
                }
                composable("searchResult"){
                    val resultViewModel = hiltViewModel<ResultViewModel>()
                    val resultState by resultViewModel.uiState.collectAsState()
                    ResultScreen(
                        resultState = resultState,
                        onEventHandled = { resultViewModel.dispatchIntent(ResultIntent.EventHandled) },
                        onNavigateToBookDetail = { book -> navController.navigate("book/${book.id}") },
                        onLikeClicked = { book -> resultViewModel.dispatchIntent(ResultIntent.LikeClicked(book))},
                        onBookClicked = { book -> resultViewModel.dispatchIntent(ResultIntent.BookTileClicked(book))}
                    )
                }
                composable(
                    route="book/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ){
                    val detailViewModel = hiltViewModel<DetailViewModel>()
                    val detailState by detailViewModel.uiState.collectAsState()
                    DetailScreen(
                        state  = detailState,
                        onEventHandled = { detailViewModel.dispatchIntent(DetailContract.DetailIntent.EventHandled) },
                        onLikeClicked = { book: BookWithLikedEntity -> detailViewModel.dispatchIntent(DetailContract.DetailIntent.LikeClicked(book))},
                    )
                }
            }
        }
    }
}
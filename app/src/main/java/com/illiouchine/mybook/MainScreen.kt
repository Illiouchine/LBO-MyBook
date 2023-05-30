package com.illiouchine.mybook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.illiouchine.mybook.ui.screen.SearchScreen
import com.illiouchine.mybook.ui.screen.SearchViewModel
import com.illiouchine.mybook.ui.screen.SearchContract.SearchIntent as Intent

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
                                Intent.Search(author = author, title = title)
                            )
                        },
                        onMyLibraryClick = {
                            searchViewModel.dispatchIntent(
                                Intent.MyLibraryClicked
                            )
                        },
                        onEventHandled = {
                            searchViewModel.dispatchIntent(
                                Intent.EventHandled
                            )
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
                    Text("MyLib")
                }
                composable("searchResult"){
                    Text("searchResult")
                }
            }
        }
    }
}
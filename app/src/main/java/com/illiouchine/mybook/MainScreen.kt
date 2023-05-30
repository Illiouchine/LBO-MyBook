package com.illiouchine.mybook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.illiouchine.mybook.ui.screen.SearchScreen

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
                composable("search") { SearchScreen() }
            }
        }
    }
}
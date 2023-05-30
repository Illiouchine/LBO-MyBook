@file:OptIn(ExperimentalMaterial3Api::class)

package com.illiouchine.mybook.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    onSearchClick: (author: String, title: String) -> Unit = { _, _ -> },
    onMyLibraryClick: () -> Unit = {}
) {
    var author by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Book store")
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
        Button(
            onClick = { onSearchClick(author, title) }
        ) {
            Text(text = "search")
        }
        Button(
            onClick = { onMyLibraryClick() }
        ) {
            Text("My Library")
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
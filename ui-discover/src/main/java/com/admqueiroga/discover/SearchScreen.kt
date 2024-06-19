package com.admqueiroga.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onMovieClick: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText = viewModel.searchText.collectAsState()

    Column {
        // Top bar with search field and back button
        TopAppBar(
            title = {
                TextField(
                    value = searchText.value,
                    onValueChange = {
                        viewModel.onSearchTextChanged(it)
                    },
                    placeholder = {
                        Text(text = "Search")
                    }
                )
            },
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // List of search results
        if (isLoading) {
            // Show loading spinner
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(searchResults.map {
                    Item(
                        id = it.id,
                        title = it.title.orEmpty(),
                        rating = it.voteAverage ?: 0f,
                        image = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                        backdropImage = "https://image.tmdb.org/t/p/original${it.backdropPath}",
                    )
                }) {
                    ItemCard(it) { onMovieClick(it.id) }
                }
            }
//            ItemListRow(
//                title = "",
//                items = searchResults.map {
//                    Item(
//                        id = it.id,
//                        title = it.title.orEmpty(),
//                        rating = it.voteAverage ?: 0f,
//                        image = "https://image.tmdb.org/t/p/w500${it.posterPath}",
//                        backdropImage = "https://image.tmdb.org/t/p/original${it.backdropPath}",
//                    )
//                },
//                onItemClick = {},
//                onMoreClick = {}
//            )
        }
    }
}
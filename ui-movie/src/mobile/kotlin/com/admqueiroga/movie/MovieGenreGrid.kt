package com.admqueiroga.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.ui_movies.R

@Composable
fun MovieGenreGrid(genreId: Long, onMovieClick: (Item) -> Unit) {
    val model = viewModel<MovieGenreViewModel>(
        factory = MovieGenreViewModel.Factory(
            genreId = genreId,
            db = MovieDb.getInstance(LocalContext.current),
            moviesApi = TmdbApiClient().movies
        )
    )
    val genre by model.genre.collectAsState()
    val items = model.pager.collectAsLazyPagingItems()
    MovieGenreGrid(genre = genre.name, items = items, onItemClick = onMovieClick)
}

@Composable
fun MovieGenreGrid(
    genre: String,
    items: LazyPagingItems<Movie>,
    onItemClick: (Item) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Text(
                    text = stringResource(
                        id = R.string.movie_grid_title,
                        formatArgs = arrayOf(genre),
                    ),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp,
                )
            }
            items(items.itemCount) { index ->
                items[index]?.let { movie ->
                    ItemCard(item = movie.asItem(), onItemClick)
                }
            }
        }
    }
}

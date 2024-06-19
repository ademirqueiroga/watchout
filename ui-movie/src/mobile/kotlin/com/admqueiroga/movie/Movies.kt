package com.admqueiroga.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemListRow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.tmdb.TmdbApiClient


fun Movie.asItem(): Item {
    return Item(
        id = id,
        title = this.title,
        subtitle = this.title,
        image = "https://image.tmdb.org/t/p/w500$posterPath",
        backdropImage = "https://image.tmdb.org/t/p/original$posterPath",
        rating = voteAverage
    )
}


@Composable
fun Movies(
    onMovieClick: (Long) -> Unit,
    onMoreClick: (MovieGenre) -> Unit,
) {
    Movies(
        moviesViewModel = viewModel(
            factory = MoviesViewModel.Factory(
                MovieDb.getInstance(LocalContext.current),
                TmdbApiClient(),
            )
        ),
        onMovieClick = onMovieClick,
        onMoreClick = onMoreClick,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Movies(
    moviesViewModel: MoviesViewModel,
    onMovieClick: (Long) -> Unit,
    onMoreClick: (MovieGenre) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(text = "Movies") }
            )
        }
    ) { paddingValues ->
        val movies by moviesViewModel.moviesByGenre.collectAsState(emptyList())
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(movies, { it.genre.id }) { genreWithMovies ->
                ItemListRow(
                    title = genreWithMovies.genre.name,
                    items = genreWithMovies.movies.map { it.asItem() },
                    onItemClick = {
                        onMovieClick(it.id)
                    },
                    onMoreClick = {
                        onMoreClick(genreWithMovies.genre)
                    }
                )
            }
//        if (loading) {
//            item(-1) {
//                CircularProgressIndicator(Modifier.padding(24.dp))
//            }
//        }
        }
    }

}
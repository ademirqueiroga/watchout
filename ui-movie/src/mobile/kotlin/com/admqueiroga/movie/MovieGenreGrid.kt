
package com.admqueiroga.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.repository.TmdbMovieRepository
import com.admqueiroga.ui_movies.R

@Composable
fun MovieGenreGrid(genreId: Long, onMovieClick: (TmdbMovie) -> Unit) {
    // TODO: Fix this
    val genre = TmdbMovieGenre(genreId, "UNKNOWN")
    val pager = remember {
        Pager(
            initialKey = 1,
            config = PagingConfig(20),
        ) {
            MoviePagingSource(TmdbMovieRepository(TmdbApiClient().movies), genres = listOf(genre))
        }
    }
    // TODO
    @SuppressLint("FlowOperatorInvokedInComposition")
    val items = pager.flow.collectAsLazyPagingItems()

    MovieGenreGrid(genre = genre, items = items, onItemClick = { })
}

@Composable
fun MovieGenreGrid(
    genre: TmdbMovieGenre,
    items: LazyPagingItems<TmdbMovie>,
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
                        formatArgs = arrayOf(genre.name.orEmpty()),
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

private fun TmdbMovie.asItem(): Item {
    return Item(
        id = id,
        title = title.orEmpty(),
        subtitle = releaseDate.orEmpty(),
        image = "https://image.tmdb.org/t/p/w500$posterPath",
        rating = voteAverage ?: 0f
    )
}

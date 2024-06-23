package com.admqueiroga.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard
import com.admqueiroga.common.compose.ui.ItemListRow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.model.TmdbPerson
import com.admqueiroga.data.tmdb.model.TmdbTvShow
import com.admqueiroga.ui_discover.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Discover(
    onMovieClick: (Long) -> Unit,
    onTvShowClick: (Long) -> Unit,
    onSearchClick: () -> Unit,
    onUserAuthRequested: (requestToken: String) -> Unit,
) {
    DiscoverScreen(
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onSearchClick = onSearchClick,
        onUserAuthRequested = onUserAuthRequested
    )
}

@Composable
private fun RowTitleWithFilters(
    title: String,
    filters: List<String>,
    onFilterSelected: (Int) -> Unit,
) {
    var selectedFilter by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column {
            Text(
                text = "Trending",
                maxLines = 1,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = LocalTextStyle.current.color.copy(alpha = 0.5f),
            )
            Text(
                text = title,
                maxLines = 1,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            FilterTabRow(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End),
                filters,
                selectedFilter,
                onFilterSelected = {
                    selectedFilter = it
                    onFilterSelected(it)
                }
            )
        }
    }
}


@Composable
private fun DiscoverContentRow(
    title: @Composable () -> Unit,
    items: List<Item>,
    onItemClick: (Long) -> Unit,
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            painterResource(R.drawable.tmdb_row_background),
            contentDescription = "",
            modifier = Modifier.offset(y = 60.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
        )
        ItemListRow(
            title = title,
            items = items,
            onItemClick = { onItemClick(it.id) },
            onShowMoreClick = {},
        )
    }
}

@Composable
private fun DiscoverScreen(
    viewModel: DiscoverViewModel = viewModel(
        factory = DiscoverViewModel.Factory(
            MovieDb.getInstance(LocalContext.current),
            TmdbApiClient().movies,
            TmdbApiClient().tvShows,
        )
    ),
    onMovieClick: (Long) -> Unit,
    onTvShowClick: (Long) -> Unit,
    onSearchClick: () -> Unit,
    onUserAuthRequested: (requestToken: String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            val filters = listOf(
                "Today" to TmdbApiService.V3.TimeWindow.Day,
                "This Week" to TmdbApiService.V3.TimeWindow.Week,
            )
            DiscoverContentRow(
                title = {
                    RowTitleWithFilters("Movies", filters.map { it.first }) { index ->
                        viewModel.loadTrendingMovies(filters[index].second)
                    }
                },
                items = state.movies.map {
                    Item(
                        id = it.id,
                        title = it.title,
                        rating = it.voteAverage,
                        image = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                        subtitle = it.releaseDate.let {
                            runCatching {
                                val inputFormat =
                                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val outputFormat =
                                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                val date = inputFormat.parse(it)
                                outputFormat.format(date)
                            }.getOrElse { "Unknown" }
                        },
                    )
                },
                onItemClick = onMovieClick
            )

        }
        item {
            val filters = listOf(
                "Today" to TmdbApiService.V3.TimeWindow.Day,
                "This Week" to TmdbApiService.V3.TimeWindow.Week,
            )
            DiscoverContentRow(
                title = {
                    RowTitleWithFilters("Tv Shows", filters.map { it.first }) { index ->
                        viewModel.loadTrendingTvShows(filters[index].second)
                    }
                },
                items = state.tvShows.map {
                    Item(
                        id = it.id,
                        title = it.name,
                        rating = it.voteAverage,
                        image = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                        subtitle = it.firstAirDate.let {
                            runCatching {
                                val inputFormat =
                                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val outputFormat =
                                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                val date = inputFormat.parse(it)
                                outputFormat.format(date)
                            }.getOrElse { "Unknown" }
                        },
                    )
                },
                onItemClick = onTvShowClick
            )

        }

        item {
            DiscoverContentRow(
                title = {
                    RowTitleWithFilters("Free to Watch", listOf("Movies", "TV")) { index ->
                        viewModel.loadFreeToWatch(index)
                    }
                },
                items = state.freeToWatch.map {
                    Item(
                        id = it.id,
                        title = it.title.orEmpty(),
                        rating = it.voteAverage ?: 0f,
                        image = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                        backdropImage = "https://image.tmdb.org/t/p/original${it.backdropPath}",
                    )
                },
                onItemClick = { }
            )
        }


    }

}

@Composable
private fun TrendingMoviesList(trendingMovies: List<TmdbMovie>) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp).height(180.dp)
    ) {
        items(trendingMovies, { it.id }) { movie ->
            ItemCard(
                item = Item(
                    id = movie.id,
                    title = movie.title.orEmpty(),
                    rating = movie.voteAverage ?: 0f,
                    backdropImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                )
            )
        }
    }
}

@Composable
private fun TrendingTvShowsList(trendingTvShows: List<TmdbTvShow>) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp).height(180.dp)
    ) {
        items(trendingTvShows) { tvShow ->
            ItemCard(
                item = Item(
                    id = tvShow.id,
                    title = tvShow.name.orEmpty(),
                    rating = tvShow.voteAverage ?: 0f,
                    backdropImage = "https://image.tmdb.org/t/p/w500${tvShow.backdropPath}",
                )
            )
        }
    }
}

@Composable
private fun TrendingPeopleList(people: List<TmdbPerson>) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp).height(180.dp)
    ) {
        items(people) { person ->
            ItemCard(
                item = Item(
                    id = person.id,
                    title = person.name.orEmpty(),
                    rating = person.popularity ?: 0f,
                    backdropImage = "https://image.tmdb.org/t/p/w500${person.profilePath}",
                )
            )
        }
    }
}
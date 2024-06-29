package com.admqueiroga.tvshow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard
import com.admqueiroga.data.tmdb.TmdbApiClient


@Composable
fun Tab(title: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        Text(title, modifier = Modifier.clickable { onClick() })
    }
}

@Composable
fun TvShows() {
    Column {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            Tab("Popular") { selectedTabIndex = 0 }
            Tab("Airing Today") { selectedTabIndex = 1 }
            Tab("On TV") { selectedTabIndex = 2 }
            Tab("Top Rated") { selectedTabIndex = 3 }
        }
        when (selectedTabIndex) {
            0 -> PopularTvShows()
//            1 -> AiringTodayTvShows()
//            2 -> OnTvTvShows()
//            3 -> TopRatedTvShows()
        }
    }
}

@Composable
fun PopularTvShows(
    tvShowViewModel: TvShowViewModel = viewModel(
        factory = TvShowViewModel.Factory(TmdbApiClient().tvShows)
    )
) {
    val items by tvShowViewModel.popular.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(items) { item ->
                ItemCard(
                    Item(
                        id = item.id,
                        title = item.name.orEmpty(),
                        subtitle = "",
                        image = "https://image.tmdb.org/t/p/w500${item.posterPath}",
                        rating = item.voteAverage ?: 0f
                    )
                )
            }
        }
    )
}

@Composable
fun TvShowGenreGrid(
//    genreId: Long
) {

}
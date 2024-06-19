package com.admqueiroga.tvshow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
            Tab("On TV"){ selectedTabIndex = 2 }
            Tab("Top Rated"){ selectedTabIndex = 3 }
        }
    }
}

@Composable
fun TvShowGenreGrid(
//    genreId: Long
) {

}
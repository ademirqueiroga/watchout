package com.admqueiroga.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun MovieDetails(
    movieId: Long,
    viewModel: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.Factory(movieId)),
) {
    viewModel.loadDetails()

    val detail by viewModel.details.collectAsState(initial = null)
    val images by viewModel.images.collectAsState(initial = null)
    val pagerState = remember(images) {
        PagerState { images?.posters?.size ?: 0 }
    }
    detail?.let { detail ->
        LazyColumn {
            item {
                Column {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth().height(250.dp),
                            model = "https://image.tmdb.org/t/p/w780${detail.backdropPath}",
                            contentScale = ContentScale.FillHeight,
                            contentDescription = null,
                        )
                        Card(modifier = Modifier.padding(16.dp)) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${detail.posterPath}",
                                contentDescription = null,
                            )
                        }
                    }
                    Text(detail.title)
                    Text("Budget: ${detail.budget}")
                    Text("Original Language: ${detail.originalLanguage}")
                    Text("Original Title: ${detail.originalTitle}")
                    Text(detail.overview.orEmpty())
                    Text("Pouplarity: ${detail.popularity}")
                    Text("Runtime: ${detail.runtime}m")
                    Text("Status: ${detail.status}")
                    Text("Tagline: ${detail.tagline}")
                    Text("Vote Average: ${detail.voteAverage}")
                    Text("Vote Count: ${detail.voteCount}")
                }
            }
            item {
                HorizontalPager(
                    modifier = Modifier.height(400.dp).background(Color.Red),
                    state = pagerState,
                ) { page ->
                    images?.let { images ->
                        AsyncImage(
                            modifier = Modifier.fillMaxHeight(),
                            model = "https://image.tmdb.org/t/p/original${images.posters[page].filePath}",
                            contentScale = ContentScale.FillHeight,
                            contentDescription = null,
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

}


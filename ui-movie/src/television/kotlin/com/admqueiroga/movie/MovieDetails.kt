package com.admqueiroga.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.admqueiroga.data.model.Movie

@Composable
fun MovieDetails(detail: Movie.Details) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Card(modifier = Modifier
            .width(400.dp)
            .aspectRatio(16 / 9f)
            .background(Color.Red)) {
//            AsyncImage(
//                model = "https://image.tmdb.org/t/p/w500${detail.posterPath}",
//                contentDescription = null,
//            )
        }

    }
//    Column {
//        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
//            AsyncImage(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp),
//                model = "https://image.tmdb.org/t/p/w780${detail.backdropPath}",
//                contentScale = ContentScale.FillHeight,
//                contentDescription = null,
//            )
//            Card(modifier = Modifier.padding(16.dp)) {
//                AsyncImage(
//                    model = "https://image.tmdb.org/t/p/w500${detail.posterPath}",
//                    contentDescription = null,
//                )
//            }
//        }
//        Text(detail.title)
//        Text("Budget: ${detail.budget}")
//        Text("Original Language: ${detail.originalLanguage}")
//        Text("Original Title: ${detail.originalTitle}")
//        Text(detail.overview.orEmpty())
//        Text("Pouplarity: ${detail.popularity}")
//        Text("Runtime: ${detail.runtime}m")
//        Text("Status: ${detail.status}")
//        Text("Tagline: ${detail.tagline}")
//        Text("Vote Average: ${detail.voteAverage}")
//        Text("Vote Count: ${detail.voteCount}")
//    }
}


@Composable
fun MovieDetails(
    movieId: Long,
    viewModel: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.Factory(movieId)),
) {
    viewModel.loadDetails()
    val detail by viewModel.details.collectAsState(initial = null)
//    detail?.let {
//        // TODO:
//        MovieDetails(detail = Movie.Details(detail.id))
//    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun MovieDetailsPreview() {
//    MovieDetails(detail = MovieDetail.sample)
}
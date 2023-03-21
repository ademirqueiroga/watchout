@file:OptIn(ExperimentalTvMaterialApi::class, ExperimentalAnimationApi::class)

package com.admqueiroga.movie

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material.ExperimentalTvMaterialApi
import androidx.tv.material.immersivelist.ImmersiveList
import coil.compose.AsyncImage
import com.admqueiroga.common.compose.model.asItem
import com.admqueiroga.data.models.Genre
import com.admqueiroga.data.models.Movie


@Composable
fun Movies(
    onMovieClick: (Long) -> Unit,
    onMoreClick: (Genre) -> Unit,
) {
    Movies(
        moviesViewModel = viewModel(),
        onMovieClick = onMovieClick,
        onMoreClick = onMoreClick,
    )
}

@Composable
fun Movies(
    moviesViewModel: MoviesViewModel,
    onMovieClick: (Long) -> Unit,
    onMoreClick: (Genre) -> Unit
) {
    val movies = remember {
        moviesViewModel.moviesByGenre
    }
    val featured = remember {
        moviesViewModel.featuredMovies
    }
    Movies(featured, movies, onMovieClick, onMoreClick)
}

@Preview(device = Devices.TABLET)
@Composable
fun FeaturedMoviesImmersiveListPreview() {
    val movies = listOf(
        Movie.sample,
        Movie.sample,
        Movie.sample,
        Movie.sample,
        Movie.sample,
        Movie.sample,
        Movie.sample,
    )
    FeaturedMoviesImmersiveList(movies) { }
}

@Composable
fun FeaturedMoviesImmersiveList(movies: List<Movie>, onMovieClick: (Long) -> Unit) {
    ImmersiveList(
        modifier = Modifier
            .fillMaxHeight(0.95f)
            .fillMaxWidth(),
        background = { index, focused ->
            if (index < movies.size) {
                val movie = movies[index]
                AnimatedContent(targetState = index) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(0.6f),
                                                Color.Black.copy(0.8f),
                                            )
                                        )
                                    )
                                }
                            },
                        model = "https://image.tmdb.org/t/p/original${movie.backdropPath}",
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.35f)
                            .wrapContentHeight(Alignment.Bottom)
                            .padding(
                                start = 32.dp,
                                top = 0.dp,
                                end = 32.dp,
                                bottom = 180.dp
                            )
                    ) {
                        Text(
                            text = movie.title.orEmpty(),
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = movie.overview.orEmpty(),
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 3
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = "Featured",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            TvLazyRow(
                contentPadding = PaddingValues(
                    start = 24.dp,
                    top = 8.dp,
                    end = 24.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    itemsIndexed(movies, { i, _ -> i }) { index, movie ->
                        FocusScalingCard(
                            modifier = Modifier
                                .focusableItem(index),
                            onClick = { onMovieClick(movie.id) },
                            shape = RoundedCornerShape(10),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .width(180.dp)
                                    .aspectRatio(16 / 9f),
                                model = movie.asItem().backdropImage,
                                contentDescription = null
                            )
                        }
                    }
                })
        }
    }

}

@Composable
fun Movies(
    featured: List<Movie>,
    genres: List<GenreWithMovies>,
    onMovieClick: (Long) -> Unit,
    onMoreClick: (Genre) -> Unit
) {
    Column() {
        FeaturedMoviesImmersiveList(movies = featured, onMovieClick)
        TvLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
//        item {
//
//        }
//        item {
//            Carousel(
//                slideCount = featured.size
//            ) { position ->
//                val movie = featured.getOrNull(position)?.asItem()
//                if (movie != null) {
//                    CarouselItem(
//                        modifier = Modifier.clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null,
//                            onClick = {
//                                onMovieClick(movie.id)
//                            }),
//                        overlayEnterTransitionStartDelayMillis = 0,
//                        background = {
//                            AsyncImage(
//                                model = movie.backdropImage,
//                                contentDescription = "",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(350.dp)
//                                    .drawWithCache {
//                                        onDrawWithContent {
//                                            drawContent()
//                                            drawRect(
//                                                Brush.horizontalGradient(
//                                                    colors = listOf(
//                                                        Color.Black.copy(alpha = 0.80f),
//                                                        Color.Black.copy(alpha = 0.60f),
//                                                        Color.Transparent,
//                                                    ),
//                                                )
//                                            )
//                                        }
//                                    }
//                            )
//                        },
//                    ) {
//                        Row(
//                            Modifier
//                                .height(350.dp)
//                                .padding(vertical = 32.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth(0.5f)
//                                    .padding(48.dp)
//                            ) {
//                                Text(
//                                    fontSize = 28.sp,
//                                    text = movie.title,
//                                    color = Color.White,
//                                    maxLines = 2,
//                                    overflow = TextOverflow.Ellipsis,
//                                )
//                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(
//                                    text = movie.subtitle,
//                                    color = Color.White.copy(0.7f),
//                                    maxLines = 3,
//                                    overflow = TextOverflow.Ellipsis,
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
            items(genres, { it.genre.id }) {
                CategoryItemsRow(
                    title = it.genre.name.orEmpty(),
                    items = it.movies.map(Movie::asItem)
                )
            }
        }

    }
}
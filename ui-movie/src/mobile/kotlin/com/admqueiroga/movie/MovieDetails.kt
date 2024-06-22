package com.admqueiroga.movie

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.admqueiroga.common.compose.ui.ScoreCircularIndicator
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre

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
    var vibrantColor by remember { mutableStateOf<Color?>(null) }
    detail?.let { movie ->
        LazyColumn {
            item {
                Column {
                    Box() {
                        BackdropImage(
                            "https://image.tmdb.org/t/p/w780${movie.backdropPath}",
                            "",
                            vibrantColor,
                            onVibrantColorChange = {
                                vibrantColor = it
                            }
                        )
                        Card(modifier = Modifier.padding(16.dp)) {
                            AsyncImage(
                                modifier = Modifier.height(200.dp),
                                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                                contentDescription = null,
                            )
                        }
                    }
                    MovieTitleText(
                        title = movie.title,
                        releaseYear = movie.releaseDate?.substringBefore("-").orEmpty(),
                        backgroundColor = vibrantColor
                    )
                    MovieGenreChips(
                        movie.genres,
                        vibrantColor
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(vibrantColor ?: Color.Transparent)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.background(Color.Yellow),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ScoreCircularIndicator(
                                score = movie.voteAverage * 10
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("User Score", modifier = Modifier.wrapContentSize(Alignment.Center))
                        }
                        Spacer(
                            modifier = Modifier.height(20.dp).width(4.dp).background(Color.Black)
                        )
                        Text("Play trailer")
                    }
                    Text("Budget: ${movie.budget}")
                    Text("Original Language: ${movie.originalLanguage}")
                    Text("Original Title: ${movie.originalTitle}")
                    Text(movie.overview.orEmpty())
                    Text("Pouplarity: ${movie.popularity}")
                    Text("Runtime: ${movie.runtime}m")
                    Text("Status: ${movie.status}")
                    Text("Tagline: ${movie.tagline}")
                    Text("Vote Average: ${movie.voteAverage}")
                    Text("Vote Count: ${movie.voteCount}")
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

// TODO: Use domain model instead of api model
@Composable
private fun MovieGenreChips(genres: List<TmdbMovieGenre>, backgroundColor: Color?) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        genres.forEachIndexed { index, genre ->
            withLink(
                LinkAnnotation.Clickable(
                    tag = "genre_$index",
                    linkInteractionListener = { annotation ->
                        Toast.makeText(
                            context,
                            (annotation as LinkAnnotation.Clickable).tag,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            ) {
                append("${genre.name} ")
            }
        }
    }
    Text(
        text = annotatedString,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        modifier = Modifier
            .background(backgroundColor ?: Color.Transparent)
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun MovieTitleText(title: String, releaseYear: String, backgroundColor: Color?) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.White)) {
                append(title)
            }
            append(" ")
            withStyle(
                SpanStyle(
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("($releaseYear)")
            }
        },
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        modifier = Modifier
            .background(backgroundColor ?: Color.Transparent)
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun BackdropImage(
    imageUrl: String,
    contentDescription: String,
    vibrantColor: Color?,
    onVibrantColorChange: (Color?) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .drawWithContent {
                drawContent()
                vibrantColor?.let { color ->
                    val gradient = Brush.horizontalGradient(
                        colors = listOf(color, Color.Transparent),
                    )
                    drawRect(brush = gradient)
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            model = imageUrl,
            contentScale = ContentScale.FillHeight,
            contentDescription = contentDescription,
            onSuccess = { successResult ->
                val bitmap = (successResult.result.drawable as? BitmapDrawable)?.bitmap
                bitmap?.let {
                    Palette.from(it.copy(Bitmap.Config.ARGB_8888, true)).generate { palette ->
                        onVibrantColorChange(palette?.vibrantSwatch?.rgb?.let { Color(it) })
                    }
                }
            }
        )
    }
}


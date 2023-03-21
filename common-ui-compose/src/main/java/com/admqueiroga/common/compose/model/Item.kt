package com.admqueiroga.common.compose.model

import androidx.compose.runtime.Immutable
import com.admqueiroga.data.tmdb.model.TmdbTvShow

@Immutable
data class Item(
    val id: Long = 0L,
    val title: String = "",
    val subtitle: String = "",
    val rating: Float = 0F,
    val image: String = "",
    val backdropImage: String = "",
)

//fun Movie.asItem(): Item {
//    return Item(
//        id = id,
//        title = this.title ?: "Unknown",
//        subtitle = overview ?: "Not released",
//        image = "https://image.tmdb.org/t/p/w500$posterPath",
//        backdropImage = "https://image.tmdb.org/t/p/original$backdropPath",
//        rating = (voteAverage ?: 0F)
//    )
//}

fun TmdbTvShow.asItem(): Item {
    return Item(
        id = id,
        title = this.name ?: "Unknown",
        subtitle = firstAirDate ?: "Not released",
        image = "https://image.tmdb.org/t/p/w500$posterPath",
        backdropImage = "https://image.tmdb.org/t/p/w500$backdropPath",
        rating = (voteAverage ?: 0F)
    )
}
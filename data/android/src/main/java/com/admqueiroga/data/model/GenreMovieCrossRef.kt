package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["genre_id", "movie_id"])
internal data class GenreMovieCrossRef(
    @ColumnInfo(name = "genre_id", index = true)
    val genreId: Long,
    @ColumnInfo(name = "movie_id", index = true)
    val movieId: Long,
)
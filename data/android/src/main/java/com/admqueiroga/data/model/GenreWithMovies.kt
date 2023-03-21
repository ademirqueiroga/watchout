package com.admqueiroga.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GenreWithMovies(
    @Embedded val genre: MovieGenre,
    @Relation(
        parentColumn = "genre_id",
        entityColumn = "movie_id",
        associateBy = Junction(GenreMovieCrossRef::class)
    )
    val movies: List<Movie>
)
package com.admqueiroga.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GenreWithTvShows(
    @Embedded val genre: TvShowGenre,
    @Relation(
        parentColumn = "genre_id",
        entityColumn = "tv_show_id",
        associateBy = Junction(GenreTvShowCrossRef::class)
    )
    val movies: List<TvShow>
)
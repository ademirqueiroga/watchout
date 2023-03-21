package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Long,
    val posterPath: String,
    val title: String,
    val voteAverage: Float,
) {
    @Entity(tableName = "movie_details")
    data class Details(
        @PrimaryKey
        val id: Long,
        val posterPath: String,
        val title: String,
        val backdropPath: String,
//        val genres: List<MovieGenre>,
        val overview: String,
        val releaseDate: String,
        val hasVideo: Boolean,
        val voteAverage: Float,
        val voteCount: Int,
        val budget: Int,
        val runtime: Int,
    )
}
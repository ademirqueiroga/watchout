package com.admqueiroga.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_movie_remote_key")
data class GenreMovieRemoteKey(
    @PrimaryKey
    @ColumnInfo("genre_id")
    val genreId: Long,
    val timestamp: Long,
    val nextKey: Int,
)
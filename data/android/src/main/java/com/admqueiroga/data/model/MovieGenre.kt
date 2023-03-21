package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_genres")
data class MovieGenre(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val id: Long,
    val name: String,
)
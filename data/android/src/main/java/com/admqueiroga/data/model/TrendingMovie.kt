package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trending_movies",
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class TrendingMovie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    val rank: Int,
    @ColumnInfo(name = "time_window")
    val timeWindow: String,
)
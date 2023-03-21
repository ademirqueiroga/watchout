package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["genre_id", "tv_show_id"])
internal data class GenreTvShowCrossRef(
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
    @ColumnInfo(name = "tv_show_id")
    val tvShowId: Long,
)
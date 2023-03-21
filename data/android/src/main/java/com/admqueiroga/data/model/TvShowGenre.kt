package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tv_show_genres")
data class TvShowGenre(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val id: Long,
    val name: String,
)
package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
class TvShow(
    @PrimaryKey
    @ColumnInfo(name = "tv_show_id")
    val id: Long,
    val name: String,
    val posterPath: String,
    val voteAverage: Float,
    val firstAirDate: String,
) {
    data class Details(val x: String)
}
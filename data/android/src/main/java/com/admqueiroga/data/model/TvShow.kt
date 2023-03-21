package com.admqueiroga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
class TvShow(
    @PrimaryKey
    val id: Long,
) {
    data class Details(
        val x: Boolean
    )
}
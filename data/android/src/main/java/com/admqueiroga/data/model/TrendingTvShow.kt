package com.admqueiroga.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trending_tv_show",
    foreignKeys = [
        ForeignKey(
            entity = TvShow::class,
            parentColumns = ["tv_show_id"],
            childColumns = ["tv_show_id"],
            onDelete = ForeignKey.CASCADE,
            deferred = true,
        )
    ]
)
data class TrendingTvShow(
    @PrimaryKey
    @ColumnInfo(name = "tv_show_id")
    val movieId: Long,
    val rank: Int,
    @ColumnInfo(name = "time_window")
    val timeWindow: String,
)
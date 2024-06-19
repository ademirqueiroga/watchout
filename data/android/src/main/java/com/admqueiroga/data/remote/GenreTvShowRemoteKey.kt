package com.admqueiroga.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_tv_show_remote_key")
data class GenreTvShowRemoteKey(
    @PrimaryKey
    @ColumnInfo("genre_id")
    val genreId: Long,
    val timestamp: Long,
    val nextKey: Int,
)
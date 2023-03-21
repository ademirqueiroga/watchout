package com.admqueiroga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.admqueiroga.data.model.GenreTvShowCrossRef

@Dao
internal interface GenreTvShowCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genreMovieCrossRefs: List<GenreTvShowCrossRef>)

    @Delete
    suspend fun delete(genreMovieCrossRef: List<GenreTvShowCrossRef>)

}
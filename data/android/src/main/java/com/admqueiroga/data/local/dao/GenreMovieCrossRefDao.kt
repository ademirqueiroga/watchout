package com.admqueiroga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.admqueiroga.data.model.GenreMovieCrossRef

@Dao
internal interface GenreMovieCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genreMovieCrossRefs: List<GenreMovieCrossRef>)

    @Delete
    suspend fun delete(genreMovieCrossRef: List<GenreMovieCrossRef>)

}
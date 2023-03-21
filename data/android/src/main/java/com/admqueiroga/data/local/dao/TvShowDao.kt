package com.admqueiroga.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.admqueiroga.data.model.GenreWithTvShows
import com.admqueiroga.data.model.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: TvShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<TvShow>)

    @Query("SELECT * FROM tv_shows")
    fun pagingSource(): PagingSource<Int, TvShow>

    @Query("DELETE FROM tv_shows")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM tv_show_genres WHERE genre_id = :genreId")
    suspend fun tvShows(genreId: Long): GenreWithTvShows?

}
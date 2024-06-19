package com.admqueiroga.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.admqueiroga.data.model.GenreWithTvShows
import com.admqueiroga.data.model.TrendingTvShow
import com.admqueiroga.data.model.TvShow
import com.admqueiroga.data.remote.GenreTvShowRemoteKey

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: TvShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<TvShow>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrending(trendingTvShow: TrendingTvShow)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrending(trendingTvShow: List<TrendingTvShow>)

    @Query("SELECT * FROM tv_shows")
    fun pagingSource(): PagingSource<Int, TvShow>

    @Transaction
    @Query(
        """
        SELECT * FROM tv_shows
        INNER JOIN genretvshowcrossref ON tv_shows.tv_show_id = genretvshowcrossref.tv_show_id
        WHERE genretvshowcrossref.genre_id = :genreId
    """
    )
    fun pagingSource(genreId: Long): PagingSource<Int, TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun logTvShowRefresh(genreTvShowRemoteKey: GenreTvShowRemoteKey)

    @Query("DELETE FROM tv_shows")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM tv_show_genres WHERE genre_id = :genreId")
    suspend fun tvShows(genreId: Long): GenreWithTvShows?

    @Query(
        """
        SELECT * 
        FROM tv_shows 
        INNER JOIN trending_tv_show ON tv_shows.tv_show_id = trending_tv_show.tv_show_id
        WHERE trending_tv_show.time_window = :timeWindow
        ORDER BY trending_tv_show.rank ASC
        """
    )
    suspend fun trending(timeWindow: String): List<TvShow>

}
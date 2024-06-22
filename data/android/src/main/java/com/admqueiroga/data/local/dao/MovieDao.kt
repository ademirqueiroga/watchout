package com.admqueiroga.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.model.TrendingMovie
import com.admqueiroga.data.remote.GenreMovieRemoteKey

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrending(trendingMovie: TrendingMovie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrending(trendingMovie: List<TrendingMovie>)

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun logMovieRefresh(genreMovieRemoteKey: GenreMovieRemoteKey)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Transaction
    @Query(
        """
        SELECT * FROM movies
        INNER JOIN genremoviecrossref ON movies.movie_id = genremoviecrossref.movie_id
        WHERE genremoviecrossref.genre_id = :genreId
    """
    )
    fun pagingSource(genreId: Long): PagingSource<Int, Movie>

    // TODO: Check how to limit movie count
    @Transaction
    @Query("SELECT * FROM movie_genres WHERE genre_id = :genreId")
    suspend fun movies(genreId: Long): GenreWithMovies?

    @Query("DELETE FROM movie_genres WHERE genre_id = :genreId")
    suspend fun delete(genreId: Long)

    @Query(
        """
        SELECT * 
        FROM movies 
        INNER JOIN trending_movies ON movies.movie_id = trending_movies.movie_id
        WHERE trending_movies.time_window = :timeWindow
        ORDER BY trending_movies.rank ASC
        """
    )
    suspend fun trending(timeWindow: String): List<Movie>

}
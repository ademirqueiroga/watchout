package com.admqueiroga.data.local.dao

import androidx.room.*
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.remote.GenreMovieRemoteKey
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieGenre: MovieGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieGenre: List<MovieGenre>)

    @Query("SELECT * FROM movie_genres")
    fun flow(): Flow<List<MovieGenre>>

    @Query("SELECT * FROM genre_movie_remote_key WHERE genre_id = :genreId")
    suspend fun remoteKeyByGenre(genreId: Long): GenreMovieRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: GenreMovieRemoteKey)

    @Query("DELETE FROM genre_movie_remote_key WHERE genre_id = :genreId")
    suspend fun deleteRemoteKeyByGenre(genreId: Long)

    @Query("SELECT * FROM movie_genres WHERE genre_id = :genreId")
    suspend fun get(genreId: Long): MovieGenre

}
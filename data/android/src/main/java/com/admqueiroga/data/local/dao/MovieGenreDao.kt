package com.admqueiroga.data.local.dao

import androidx.room.*
import com.admqueiroga.data.model.MovieGenre
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
    suspend fun remoteKeyByGenre(genreId: Long): com.admqueiroga.data.GenreMovieRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: com.admqueiroga.data.GenreMovieRemoteKey)

    @Query("DELETE FROM genre_movie_remote_key WHERE genre_id = :genreId")
    suspend fun deleteRemoteKeyByGenre(genreId: Long)

}
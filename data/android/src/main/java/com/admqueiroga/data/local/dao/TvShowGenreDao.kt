package com.admqueiroga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.model.TvShowGenre
import com.admqueiroga.data.remote.GenreMovieRemoteKey
import com.admqueiroga.data.remote.GenreTvShowRemoteKey
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: TvShowGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genres: List<TvShowGenre>)

    @Query("SELECT * FROM tv_show_genres")
    fun flow(): Flow<List<TvShowGenre>>

    @Query("SELECT * FROM genre_tv_show_remote_key WHERE genre_id = :genreId")
    suspend fun remoteKeyByGenre(genreId: Long): GenreTvShowRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: GenreTvShowRemoteKey)

    @Query("DELETE FROM genre_tv_show_remote_key WHERE genre_id = :genreId")
    suspend fun deleteRemoteKeyByGenre(genreId: Long)

    @Query("SELECT * FROM tv_show_genres WHERE genre_id = :genreId")
    suspend fun get(genreId: Long): TvShowGenre

}
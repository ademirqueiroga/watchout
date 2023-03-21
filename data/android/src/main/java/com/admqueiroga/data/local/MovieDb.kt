package com.admqueiroga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.admqueiroga.data.local.dao.*
import com.admqueiroga.data.model.*

@Database(
    entities = [
        Movie::class,
        Movie.Details::class,
        com.admqueiroga.data.GenreMovieRemoteKey::class,
        MovieGenre::class,
        TvShowGenre::class,
        GenreMovieCrossRef::class,
        GenreTvShowCrossRef::class,
    ],
    exportSchema = false, // TODO: Export
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun movieGenreDao(): MovieGenreDao
    abstract fun tvShowGenreDao(): TvShowGenreDao
    internal abstract fun genreMovieCrossRefDao(): GenreMovieCrossRefDao
    internal abstract fun genreTvShowCrossRefDao(): GenreTvShowCrossRefDao

    companion object {
        private var instance: MovieDb? = null

        @Synchronized
        fun getInstance(context: Context): MovieDb {
            val currentInstance = instance
            if (currentInstance != null) {
                return currentInstance
            }
            instance = Room.databaseBuilder(
                context,
                MovieDb::class.java,
                "${context.packageName}-movie-db"
            ).build()
            return instance!!
        }
    }

}

package com.admqueiroga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.admqueiroga.data.local.dao.*
import com.admqueiroga.data.model.*
import com.admqueiroga.data.remote.GenreMovieRemoteKey
import com.admqueiroga.data.remote.GenreTvShowRemoteKey

@Database(
    entities = [
        Movie::class,
        Movie.Details::class,
        TrendingMovie::class,
        MovieGenre::class,
        GenreMovieCrossRef::class,
        GenreMovieRemoteKey::class,

        TvShow::class,
        TrendingTvShow::class,
        TvShowGenre::class,
        GenreTvShowCrossRef::class,
        GenreTvShowRemoteKey::class,
    ],
    exportSchema = false, // TODO: Export
    version = 2
)
@TypeConverters(Converters::class)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
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
            ).fallbackToDestructiveMigration()
                .build()
            return instance!!
        }
    }

}

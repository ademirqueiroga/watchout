package com.admqueiroga.data.tmdb

import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    interface V3 {

        companion object {
            const val V3 = "3"
        }

        interface Movies {

            @GET("$V3/genre/movie/list")
            suspend fun genres(
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbMovieGenre.Payload, TmdbApiError>

            @GET("$V3/discover/movie")
            suspend fun find(
                @Query("page") page: Int,
                @Query("with_genres") genres: String?,
            ): NetworkResponse<TmdbPage<TmdbMovie>, TmdbApiError>

            @GET("$V3/movie/popular")
            suspend fun popular(
                @Query("page") page: Int? = 1,
                @Query("region") region: String? = null,
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbPage<TmdbMovie>, TmdbApiError>

            @GET("$V3/movie/{movieId}")
            suspend fun detail(
                @Path("movieId") movieId: Long,
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbMovieDetail, TmdbApiError>

        }

        interface TvShows {

            @GET("$V3/genre/tv/list")
            suspend fun genres(
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbTvShowGenre.Payload, TmdbApiError>

            @GET("$V3/discover/tv")
            suspend fun find(
                @Query("page") page: Int,
                @Query("with_genres") genres: String?,
            ): NetworkResponse<TmdbPage<TmdbTvShow>, TmdbApiError>

        }

        interface People {

            @GET("$V3/person/popular")
            suspend fun popular(
                @Query("page") page: Int? = 1,
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbPage<TmdbPerson>, TmdbApiError>

        }

    }

}
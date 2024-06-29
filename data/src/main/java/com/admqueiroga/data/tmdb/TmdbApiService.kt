package com.admqueiroga.data.tmdb

import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.model.TmdbAccount
import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.model.TmdbMovieDetail
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbMovieImages
import com.admqueiroga.data.tmdb.model.TmdbNewSessionRequest
import com.admqueiroga.data.tmdb.model.TmdbPage
import com.admqueiroga.data.tmdb.model.TmdbPerson
import com.admqueiroga.data.tmdb.model.TmdbRequestToken
import com.admqueiroga.data.tmdb.model.TmdbSession
import com.admqueiroga.data.tmdb.model.TmdbTvShow
import com.admqueiroga.data.tmdb.model.TmdbTvShowGenre
import com.squareup.moshi.Json
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    interface V3 {

        enum class MonetizationType {
            @Json(name = "flatrate")
            Flatrate,

            @Json(name = "free")
            Free,

            @Json(name = "ads")
            Ads,

            @Json(name = "rent")
            Rent,

            @Json(name = "buy")
            Buy,
            ;
        }

        enum class TimeWindow {
            @Json(name = "day")
            Day,

            @Json(name = "week")
            Week,
            ;
        }

        interface Search {
            @GET("$V3/search/movie")
            suspend fun movies(
                @Query("query") query: String,
                @Query("page") page: Int = 1,
            ): NetworkResponse<TmdbPage<TmdbMovie>, TmdbApiError>
        }

        interface Trending {

            @GET("$V3/trending/movie/{time_window}")
            @Deprecated("Use Movies api instead")
            suspend fun movies(
                @Path("time_window") timeWindow: TimeWindow = TimeWindow.Day
            ): NetworkResponse<TmdbPage<TmdbMovie>, TmdbApiError>

            @GET("$V3/trending/tv/{time_window}")
            @Deprecated("Use TvShow api instead")
            suspend fun tvShows(
                @Path("time_window") timeWindow: TimeWindow = TimeWindow.Day
            ): NetworkResponse<TmdbPage<TmdbTvShow>, TmdbApiError>

            @GET("$V3/trending/people/{time_window}")
            suspend fun people(
                @Path("time_window") timeWindow: TimeWindow = TimeWindow.Day
            ): NetworkResponse<TmdbPage<TmdbPerson>, TmdbApiError>
        }

        interface Movies {

            @GET("$V3/trending/movie/{time_window}")
            suspend fun trending(
                @Path("time_window") timeWindow: TimeWindow = TimeWindow.Day,
            ): NetworkResponse<TmdbPage<TmdbMovie>, TmdbApiError>

            @GET("$V3/genre/movie/list")
            suspend fun genres(
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbMovieGenre.Payload, TmdbApiError>

            @GET("$V3/discover/movie")
            suspend fun discover(
                @Query("page") page: Int,
                @Query("with_genres") genres: String?,
                @Query("with_watch_monetization_types") monetizationType: MonetizationType? = null,
                @Query("watch_region") watchRegion: String? = "US",
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

            @GET("$V3/movie/{movieId}/images")
            suspend fun images(
                @Path("movieId") movieId: Long,
                @Query("language") language: String? = "en",
            ): NetworkResponse<TmdbMovieImages, TmdbApiError>

        }

        interface TvShows {

            @GET("$V3/trending/tv/{time_window}")
            suspend fun trending(
                @Path("time_window") timeWindow: TimeWindow = TimeWindow.Day
            ): NetworkResponse<TmdbPage<TmdbTvShow>, TmdbApiError>

            @GET("$V3/genre/tv/list")
            suspend fun genres(
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbTvShowGenre.Payload, TmdbApiError>

            @GET("$V3/discover/tv")
            suspend fun discover(
                @Query("page") page: Int,
                @Query("with_genres") genres: String?,
                @Query("with_watch_monetization_types") monetizationType: MonetizationType? = null,
                @Query("watch_region") watchRegion: String? = "US",
            ): NetworkResponse<TmdbPage<TmdbTvShow>, TmdbApiError>

        }

        interface People {

            @GET("$V3/person/popular")
            suspend fun popular(
                @Query("page") page: Int? = 1,
                @Query("language") language: String = "en-US",
            ): NetworkResponse<TmdbPage<TmdbPerson>, TmdbApiError>

        }

        interface Auth {
            @GET("$V3/authentication/token/new")
            suspend fun newToken(): NetworkResponse<TmdbRequestToken, TmdbApiError>

            @FormUrlEncoded
            @POST("$V3/authentication/session/new")
            suspend fun newSession(request: TmdbNewSessionRequest): NetworkResponse<TmdbSession, TmdbApiError>

        }

        interface Account {
            @GET("$V3/account")
            suspend fun details(
                @Query("session_id") sessionId: String,
            ): NetworkResponse<TmdbAccount, TmdbApiError>
        }

    }

    companion object {
        const val V3 = "3"
    }

}
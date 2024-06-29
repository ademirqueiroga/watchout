package com.admqueiroga.data.tmdb

import com.admqueiroga.data.NetworkResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TmdbApiClient(token: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNWUxZmExMzkwMjFjZThmZmJkYTdhM2NhMDYyZjJmYyIsInN1YiI6IjU4ZDVjNTQzYzNhMzY4MTI0ZjAzMmVlOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.K5XUd2RIsEm36iDLGwNISmz08abBAQ-Qqe0GzuqyJcI") {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
                .also {
                    println(it.url())
                }
            chain.proceed(request)
        }
        .build()

    private val moshiConverter = MoshiConverterFactory.create()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(moshiConverter)
        .addConverterFactory(EnumConverterFactory())
        .addCallAdapterFactory(NetworkResponseCallAdapterFactory())
        .baseUrl("https://api.themoviedb.org/")
        .build()

    val search: TmdbApiService.V3.Search = retrofit.create(TmdbApiService.V3.Search::class.java)
    val trending: TmdbApiService.V3.Trending = retrofit.create(TmdbApiService.V3.Trending::class.java)
    val movies: TmdbApiService.V3.Movies = retrofit.create(TmdbApiService.V3.Movies::class.java)
    val tvShows: TmdbApiService.V3.TvShows = retrofit.create(TmdbApiService.V3.TvShows::class.java)
    val people: TmdbApiService.V3.People = retrofit.create(TmdbApiService.V3.People::class.java)
    val auth: TmdbApiService.V3.Auth = retrofit.create(TmdbApiService.V3.Auth::class.java)
    val account: TmdbApiService.V3.Account = retrofit.create(TmdbApiService.V3.Account::class.java)

}
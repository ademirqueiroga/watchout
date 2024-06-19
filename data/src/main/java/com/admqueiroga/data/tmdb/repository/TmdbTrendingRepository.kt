//package com.admqueiroga.data.tmdb.repository
//
//import com.admqueiroga.data.tmdb.TmdbApiService
//
//class TmdbTrendingRepository(
//    private val api: TmdbApiService.V3.Trending,
//) {
//    suspend fun all(
//        timeWindow: TmdbApiService.V3.Trending.TimeWindow? = null
//    ) = api.all(timeWindow)
//}
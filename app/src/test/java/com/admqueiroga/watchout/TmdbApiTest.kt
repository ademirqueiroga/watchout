//package com.admqueiroga.watchout
//
//import com.admqueiroga.data.api.TmdbApiClient
//import kotlinx.coroutines.runBlocking
//import org.junit.Test
//
//import org.junit.Assert.*
//
//
//class TmdbApiTest {
//    @Test
//    fun `load movie genres`() {
//        val client = com.admqueiroga.data.api.TmdbApiClient()
//        val genres = runBlocking {
//            client.movies.genres()
//        }
//        println(genres)
//    }
//
//    @Test
//    fun `load movies of a genre`() {
//        val client = com.admqueiroga.data.api.TmdbApiClient()
//        val movies = runBlocking {
//            val genres = client.movies.genres()
//            client.movies.find(1, genres.genres.first().id.toString())
//        }
//        println(movies)
//    }
//
//    @Test
//    fun `load tv shows genres`() {
//        val client = com.admqueiroga.data.api.TmdbApiClient()
//        val genres = runBlocking {
//            client.tvShows.genres()
//        }
//        println(genres)
//    }
//
//    @Test
//    fun `load tv shows of a genre`() {
//        val client = com.admqueiroga.data.api.TmdbApiClient()
//        val movies = runBlocking {
//            val genres = client.tvShows.genres()
//            client.tvShows.find(1, genres.genres.first().id.toString())
//        }
//        println(movies)
//    }
//}
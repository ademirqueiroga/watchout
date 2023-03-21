//package com.admqueiroga.watchout
//
//import com.admqueiroga.data.repositories.MovieRepository
//import com.admqueiroga.data.api.TmdbApiClient
//import kotlinx.coroutines.runBlocking
//import org.junit.Test
//
//class MovieRepositoryTest {
//
//    val repo = MovieRepository(TmdbApiClient().movies)
//
//    @Test
//    fun `load movie genres`() {
//        runBlocking {
//            print(repo.genres(com.admqueiroga.data.models.Language.PT_BR))
//        }
//    }
//
//    @Test
//    fun `load movie page`() {
//        runBlocking {
//            val genres = repo.genres()
//            println(repo.movies(1, genres.first()))
//        }
//    }
//
//}
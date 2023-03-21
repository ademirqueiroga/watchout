package com.admqueiroga.data.tmdb.repository

import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbPage
import com.admqueiroga.data.tmdb.model.TmdbPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TmdbPeopleRepository(private val api: TmdbApiService.V3.People) {

    suspend fun popular(page: Int) = api.popular(page)

}
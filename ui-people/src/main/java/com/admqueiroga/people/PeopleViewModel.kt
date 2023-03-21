package com.admqueiroga.people

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.repository.TmdbPeopleRepository

@Stable
class PeopleViewModel : ViewModel() {

    val source = PeoplePagingDataSource(TmdbPeopleRepository(TmdbApiClient().people)).pager.flow
        .cachedIn(viewModelScope)

}
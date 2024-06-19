package com.admqueiroga.people

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.admqueiroga.data.tmdb.model.TmdbPerson

@Composable
fun People(
    onPersonClick: (personId: Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("People") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            People(model = viewModel(), onPersonClick)
        }
    }
}

@Composable
private fun People(model: PeopleViewModel, onPersonClick: (personId: Long) -> Unit) {
    val people = model.source.collectAsLazyPagingItems()
    People(people = people, onPersonClick)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun People(people: LazyPagingItems<TmdbPerson>, onPersonClick: (personId: Long) -> Unit) {
    var expansionMap = remember {
        mutableStateMapOf<Long, Boolean>()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = people.itemCount,
            key = { i -> people[i]!!.id },
            span = { if (expansionMap[people[it]!!.id] == true) GridItemSpan(2) else GridItemSpan(1) }
        ) { i ->
            PersonCard(people[i], modifier = Modifier
                .animateItem()
                .animateContentSize()) { person ->
                person?.let {
                    expansionMap[it.id] = expansionMap[it.id] != true
                    onPersonClick(it.id)
                }
            }
        }
    }
}
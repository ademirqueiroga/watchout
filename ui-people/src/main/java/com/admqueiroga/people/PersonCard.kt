@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.people

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.admqueiroga.data.tmdb.model.TmdbPerson

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonCard(person: TmdbPerson?, modifier: Modifier = Modifier, onClick: (TmdbPerson?) -> Unit) {
    Column(modifier = modifier.width(160.dp)) {
        Box {
            Card(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = { onClick(person) }
            ) {
                if (person != null) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + person.profilePath,
                        contentDescription = "Movie poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(2 / 3f)
                            .background(Color.Gray),
                    )
                }
            }
        }
        Text(
            text = person?.name.orEmpty(),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
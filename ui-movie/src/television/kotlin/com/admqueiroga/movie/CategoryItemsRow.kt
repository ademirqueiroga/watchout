package com.admqueiroga.movie

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemCard

@Composable
fun CategoryItemsRow(
    modifier: Modifier = Modifier,
    title: String,
    items: List<Item>,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            fontSize = 18.sp,
            text = title,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TvLazyRow(
            contentPadding = PaddingValues(horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(items, { it.id }) { item ->
                    FocusScalingCard {
                        ItemCard(item =item, onClick = {})
                    }
                }
            })
    }
}
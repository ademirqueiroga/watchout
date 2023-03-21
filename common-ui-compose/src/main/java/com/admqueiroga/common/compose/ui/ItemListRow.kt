@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common_ui_compose.R

private const val TYPE_ITEM = 0
private const val TYPE_MORE = 1

@Composable
fun ItemListRow(
    title: String?,
    items: List<Item>,
    itemDisplayCount: Int = 10,
    onItemClick: (Item) -> Unit,
    onMoreClick: () -> Unit,
) {
    ItemListRow(
        modifier = Modifier,
        title = title,
        items = items,
        itemDisplayCount = itemDisplayCount,
        onItemClick = onItemClick,
        onMoreClick = onMoreClick,
    )
}

@Composable
private fun ItemListRow(
    modifier: Modifier = Modifier,
    title: String?,
    items: List<Item>,
    itemDisplayCount: Int = 10,
    onItemClick: (Item) -> Unit,
    onMoreClick: () -> Unit,
) {
    Column(modifier) {
        if (title != null) {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 16.dp),
                maxLines = 1,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(
                count = items.size.coerceAtMost(itemDisplayCount),
                key = { index -> items[index].id },
                contentType = { TYPE_ITEM }
            ) { index ->
                ItemCard(item = items[index], onItemClick)
            }
            if (items.size > itemDisplayCount) {
                item(
                    contentType = TYPE_MORE
                ) {
                    Card(
                        shape = CircleShape,
                        backgroundColor = MaterialTheme.colors.onBackground,
                        onClick = onMoreClick,
                    ) {
                        Image(
                            modifier = Modifier.size(48.dp, 48.dp),
                            painter = painterResource(id = R.drawable.ic_add_light),
                            contentDescription = stringResource(id = R.string.cd_more_items),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemListRowPreview() {
    val items = listOf(
        Item(0, "Batman", "10 Mar. 2022", 8.0f),
        Item(1, "Super Man", "10 Mar. 2022", 7.0f),
        Item(2, "Joker", "10 Mar. 2022", 7.5f),
        Item(3, "Wonderwoman", "10 Mar. 2022", 9.3f),
    )
    ItemListRow(title = "Title", items = items, onItemClick = {}, onMoreClick = {})
}
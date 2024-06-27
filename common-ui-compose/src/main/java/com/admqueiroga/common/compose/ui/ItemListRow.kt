@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.common_ui_compose.R

private const val TYPE_ITEM = 0
private const val TYPE_MORE = 1

object ItemListRowDefaults {
    @Composable
    fun Title(title: String, modifier: Modifier = Modifier) {
        Text(
            text = title,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 1,
            style = MaterialTheme.typography.h5
        )
    }
}

/**
 * Composable that displays a row of items with a title. This is typically used in a list to
 * display a subset of items with an option to show more.
 *
 * @param items The list of items to display.
 * @param title The title of the row.
 * @param onItemClick Callback invoked when an item is clicked.
 * @param onShowMoreClick Callback invoked when the "Show More" button is clicked.
 * @param modifier The modifier to be applied to the row.
 * @param maxItemsToDisplay The maximum number of items to display before showing the "Show More" button.
 */
@Composable
fun ItemListRow(
    items: List<Item>,
    title: String,
    onItemClick: (Item) -> Unit,
    onShowMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxItemsToDisplay: Int = 10,
) {
    ItemListRow(
        items = items,
        modifier = modifier,
        title = {
            ItemListRowDefaults.Title(title)
        },
        maxItemsToDisplay = maxItemsToDisplay,
        onItemClick = onItemClick,
        onShowMoreClick = onShowMoreClick,
    )
}

/**
 * Composable function to display a row of items.
 *
 * @param items The list of items to display.
 * @param onItemClick The callback to invoke when an item is clicked.
 * @param modifier The modifier to apply to the composable.
 * @param title The title of the row.
 */
@Composable
fun ItemListRow(
    items: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
) {
    ItemListRow(
        items = items,
        onItemClick = onItemClick,
        onShowMoreClick = {},
        modifier = modifier,
        title = title,
        maxItemsToDisplay = items.size
    )
}

/**
 * Composable function to display a row of items with a title, a maximum number of items to display,
 * and a "Show More" button if there are more items than the maximum.
 *
 * This function creates a `Column` that contains the title (if provided), and a `LazyRow` that displays
 * the items. If there are more items than the maximum, a "Show More" button is displayed.
 *
 * @param items The list of items to display.
 * @param onItemClick The callback to be invoked when an item is clicked.
 * @param modifier The modifier to be applied to the root element.
 * @param onShowMoreClick The callback to be invoked when the "Show More" button is clicked.
 * @param title The optional title to be displayed for the row.
 * @param maxItemsToDisplay The maximum number of items to display before showing the "Show More" button.
 */
@Composable
fun ItemListRow(
    items: List<Item?>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier,
    onShowMoreClick: (() -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    maxItemsToDisplay: Int = 10,
) {
    Column(modifier) {
        if (title != null) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                title()
            }
        }
        LazyRow(
            modifier = Modifier.graphicsLayer(clip = false),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(
                count = minOf(items.size, maxItemsToDisplay),
                key = { index -> items[index]?.id ?: index },
                contentType = { TYPE_ITEM }
            ) { index ->
                ItemCard(item = items[index], onClick = onItemClick)
            }
            if (items.size > maxItemsToDisplay && onShowMoreClick != null) {
                item(contentType = TYPE_MORE) {
                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                        ShowMoreButton(onClick = onShowMoreClick)
                        // Add space needed to center the button with the images. [AQ]
                        ItemCardDefaults.Details("", "")
                    }
                }
            }
        }
    }
}

/**
 * A Composable function that displays a button with a plus icon to indicate that there are more items to show.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
private fun ShowMoreButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.testTag(ItemListRow.tagForMore()),
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.onBackground,
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_add_light),
            contentDescription = stringResource(id = R.string.cd_more_items),
        )
    }
}

object ItemListRow {
    fun tagForItem(item: Item) = "item_${item.id}"
    fun tagForMore() = "item_more"
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
    WatchOutTheme {
        ItemListRow(
            modifier = Modifier.height(300.dp),
            title = "Title",
            items = items,
            onItemClick = {},
            onShowMoreClick = {}
        )

    }
}
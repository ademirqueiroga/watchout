@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.common_ui_compose.R


object ItemCardDefaults {

    /**
     * Composable function to display an image with a score indicator.
     *
     * @param url The URL of the image.
     * @param score The score to display.
     * @param contentDescription The content description for the image.
     * @param aspectRatio The aspect ratio of the image.
     * @param modifier The modifier to be applied to the image.
     */
    @Composable
    fun ImageWithScore(
        url: String,
        score: Float,
        contentDescription: String,
        aspectRatio: Float,
        modifier: Modifier = Modifier
    ) {
        Box {
            val scoreSize = 40.dp
            Card(
                modifier = modifier
                    .padding(bottom = scoreSize / 2)
                    .testTag("ItemCard"),
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = stringResource(
                        id = R.string.cd_item_image,
                        formatArgs = arrayOf(contentDescription)
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.aspectRatio(aspectRatio).background(Color.Gray),
                )
            }
            ScoreCircularIndicator(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.BottomStart),
                scorePercentage = score
            )
        }

    }

    /**
     * Composable function to display details with a title and subtitle.
     *
     * @param title The title of the details.
     * @param subtitle The subtitle of the details.
     * @param modifier The modifier to apply to the details container.
     */
    @Composable
    fun Details(title: String, subtitle: String, modifier: Modifier = Modifier) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = subtitle,
                maxLines = 1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }

}

/**
 * A composable that displays an item card with an image, score, title, and subtitle.
 *
 * @param item The item to display.
 * @param onClick The callback to be invoked when the item is clicked.
 * @param modifier The modifier to be applied to the card.
 * @param imageAspectRatio The aspect ratio of the image.
 * @param imageContent The content to be displayed in the image area.
 * @param detailsContent The content to be displayed in the details area.
 */
@Composable
fun ItemCard(
    item: Item?,
    modifier: Modifier = Modifier,
    onClick: ((Item) -> Unit)? = null,
    imageAspectRatio: Float = 2f / 3f,
    imageContent: @Composable () -> Unit = {
        val calculatedRatio = remember(imageAspectRatio) {
            imageAspectRatio
        }
        if (item == null) {
            Card(
                modifier = modifier
                    .size(200.dp, 280.dp)
                    .padding(bottom = 40.dp / 2)
                    .testTag("ItemCard"),
            ){
                Box(modifier.background(Color.Gray))
            }
        } else {
            val calculatedScore = remember(item.rating) {
                item.rating / 10f
            }
            ItemCardDefaults.ImageWithScore(
                url = item.image,
                contentDescription = item.title,
                score = calculatedScore,
                aspectRatio = calculatedRatio,
                modifier = if (onClick != null) Modifier.clickable { onClick(item) } else Modifier
            )
        }
    },
    detailsContent: @Composable () -> Unit = {
        if (item == null) {
            Column(modifier = modifier.padding(8.dp)) {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 2.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                )
                Text(
                    text = "",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(vertical = 2.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                )
            }
        } else {
            ItemCardDefaults.Details(
                title = item.title,
                subtitle = item.subtitle
            )
        }
    },
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val imagePlaceable = subcompose("image") {
            imageContent()
        }[0].measure(constraints)
        val detailsPlaceable = subcompose("details") {
            detailsContent()
        }[0].measure(Constraints.fixedWidth(imagePlaceable.width))

        layout(
            width = imagePlaceable.width,
            height = imagePlaceable.height + detailsPlaceable.height,
        ) {
            imagePlaceable.placeRelative(0, 0)
            detailsPlaceable.placeRelative(0, imagePlaceable.height)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    WatchOutTheme {
        ItemCard(
            item = Item(
                title = "Trigger Warning",
                subtitle = "Jun 21, 2024",
                rating = 5.5f,
                image = ""
            ),
            onClick = {},
            modifier = Modifier.background(Color.White)
        )
    }
}

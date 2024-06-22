@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.common_ui_compose.R


@Composable
fun ItemCard(item: Item, onClick: (Item) -> Unit) {
    Column(modifier = Modifier.width(160.dp).testTag(ItemListRow.tagForItem(item))) {
        Box(contentAlignment = Alignment.BottomStart) {
            val scoreSize = 40.dp
            Card(
                modifier = Modifier.padding(bottom = scoreSize / 2).testTag("ItemCard"),
                onClick = { onClick(item) },
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = stringResource(
                        id = R.string.cd_item_image,
                        formatArgs = arrayOf(item.title)
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(2 / 3f)
                        .background(Color.Gray),
                )
            }
            ScoreCircularIndicator(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(scoreSize),
                score = item.rating * 10
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Text(
                text = item.title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = item.subtitle,
                maxLines = 1,
                color = Color.Gray,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    WatchOutTheme {
        ItemCard(
            item = Item(
                title = "Preview",
                backdropImage = ""
            )
        ) {}
    }
}

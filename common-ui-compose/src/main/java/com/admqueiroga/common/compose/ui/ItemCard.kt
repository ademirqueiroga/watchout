@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
private fun ScoreCircularIndicator(
    modifier: Modifier = Modifier,
    @FloatRange(0.0, 1.0) score: Float
) {
    val (trackColor, progressColor) = remember(score) {
        when (score) {
            in 0f..0.4f -> Color(0xFF571435) to Color(0xFFdb2360)
            in 0.41f..0.7f -> Color(0xFF423d0f) to Color(0xFFd2d531)
            else -> Color(0xFF204529) to Color(0xFF21d07a)
        }
    }
    val strokeWidth = 2.dp
    val circularProgressModifier = Modifier.padding(2.dp)
    Surface(
        modifier = modifier,
        elevation = 1.dp,
        shape = CircleShape,
        color = MaterialTheme.colors.onSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = circularProgressModifier,
                progress = 1f,
                strokeWidth = strokeWidth,
                color = trackColor
            )
            CircularProgressIndicator(
                modifier = circularProgressModifier,
                progress = score / 100,
                strokeWidth = strokeWidth,
                color = progressColor,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = AnnotatedString.Builder().apply {
                    append(score.toInt().toString())
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
                            fontStyle = FontStyle.Normal,
                            fontSize = 8.sp,
                            baselineShift = BaselineShift(0.35f),
                        )
                    ) {
                        append("%")
                    }
                }.toAnnotatedString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.surface,
            )
        }
    }
}

@Composable
fun ItemCard(item: Item, onClick: (Item) -> Unit) {
    Column(modifier = Modifier.width(160.dp)) {
        Box(contentAlignment = Alignment.BottomStart) {
            val scoreSize = 40.dp
            Card(
                modifier = Modifier.padding(bottom = scoreSize / 2),
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
                        .fillMaxSize()
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

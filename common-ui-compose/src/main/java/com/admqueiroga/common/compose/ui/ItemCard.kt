@file:OptIn(ExperimentalMaterialApi::class)

package com.admqueiroga.common.compose.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common_ui_compose.R

@Composable
fun ItemCard(item: Item, onClick: (Item) -> Unit) {
    var expandBadge by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.width(160.dp)) {
        Box(contentAlignment = Alignment.BottomStart) {
            Card(
                modifier = Modifier.padding(bottom = 20.dp),
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
                        .aspectRatio(2/3f)
                        .background(Color.Gray),
                )
            }
            val badgeWidthModifier =
                if (expandBadge) Modifier.fillMaxWidth() else Modifier.width(40.dp)
            Card(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .then(badgeWidthModifier)
                    .height(40.dp),
                shape = CircleShape,
                onClick = {
                    expandBadge = !expandBadge
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(item.rating / 10, Modifier.padding(2.dp))
                    Text(
                        text = item.rating.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium, elevation = 1.dp
        ) {
            Column(
                Modifier
                    .padding(start = 8.dp)
                    .clickable { onClick(item) }
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
}
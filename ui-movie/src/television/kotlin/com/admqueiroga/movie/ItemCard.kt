package com.admqueiroga.movie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admqueiroga.common.compose.model.Item


@Composable
fun FocusScalingCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    onClick: () -> Unit = {},
    content: @Composable (focused: Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(targetValue = if (focused) 1.1f else 1.0f)
    Card(
        modifier = modifier
            .wrapContentSize()
            .scale(scale)
            .focusable(true, interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            ),
        shape = shape
    ) {
        content(focused)
    }
}

@Composable
fun ItemCard(item: Item) {
    FocusScalingCard { focused ->
        Column(
            modifier = Modifier.background(if (focused) Color.Red else Color.White)
        ) {
            Box(
                modifier = Modifier
                    .width(208.dp)
                    .aspectRatio(16 / 9f)
                    .background(Color.Gray)
            )
            Text(
                text = item.title, fontSize = 16.sp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            )
        }
    }
}
package com.admqueiroga.discover

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import androidx.compose.ui.util.fastSumBy

enum class FilterSlots {
    Tabs,
    Indicator
}

data class FilterPosition(
    val left: Dp, val width: Dp
)

@Composable
fun FilterTabItem(
    text: String,
    isSelected: Boolean,
    selectedTextBrush: Brush,
    unselectedTextBrush: Brush,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable(
                interactionSource = remember(isSelected) { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        fontWeight = FontWeight.SemiBold,
        text = text,
        style = TextStyle(
            brush = when (isSelected) {
                true -> selectedTextBrush
                else -> unselectedTextBrush
            }
        )
    )
}

@Composable
fun FilterTabRow(
    filters: List<String>,
    selectedTabIndex: Int,
    onFilterSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabContent: @Composable () -> Unit = {
        val gradientColors = listOf(Color(0xFFc0fecf), Color(0xFF1ed5a9))
        val unselectedTextColor = Color(0xFF032541)
        filters.fastForEachIndexed { index, filter ->
            FilterTabItem(
                text = filter,
                isSelected = selectedTabIndex == index,
                selectedTextBrush = Brush.linearGradient(colors = gradientColors),
                unselectedTextBrush = SolidColor(unselectedTextColor),
                onClick = { onFilterSelected(index) }
            )
        }
    }
) {
    val borderColor = Color(0xFF032541)
    Surface(
        modifier = modifier.selectableGroup(),
        shape = CircleShape,
        border = BorderStroke(1.dp, borderColor),
        elevation = 1.dp
    ) {
        SubcomposeLayout(
            modifier = Modifier.wrapContentWidth()
        ) { constraints ->
            val tabMeasurables = subcompose(FilterSlots.Tabs, tabContent)
            val tabPlaceables = tabMeasurables.fastMap { measurable ->
                measurable.measure(constraints)
            }
            var xPosition = 0
            val tabPositions = List(tabMeasurables.size) { index ->
                val left = xPosition
                val width = tabPlaceables[index].width
                xPosition += width
                FilterPosition(left.toDp(), width.toDp())
            }
            val tabRowWidth = tabPlaceables.fastSumBy { it.width }
            val tabRowHeight = tabPlaceables.fastMaxBy { it.height }?.height ?: 0
            layout(tabRowWidth, tabRowHeight) {
                subcompose(FilterSlots.Indicator) {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .fillMaxHeight()
                            .background(borderColor, CircleShape)
                    )
                }.fastForEach {
                    it.measure(Constraints.fixed(tabRowWidth, tabRowHeight))
                        .placeRelative(0, 0)
                }
                tabPlaceables.fastForEachIndexed { index, placeable ->
                    placeable.placeRelative(tabPlaceables.take(index).fastSumBy { it.width }, 0)
                }
            }
        }
    }
}


fun Modifier.tabIndicatorOffset(
    currentTabPosition: FilterPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val animationSpec = tween<Dp>(durationMillis = 250, easing = FastOutSlowInEasing)
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = animationSpec
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = animationSpec
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
        .width(currentTabWidth)
}
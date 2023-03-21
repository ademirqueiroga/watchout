package com.admqueiroga.common.compose.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun RowScope.BottomNavItem(
    @StringRes labelRes: Int,
    @DrawableRes iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        label = {
            Text(stringResource(id = labelRes))
        },
        icon = {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                alpha = if (selected) 1f else 0.4f,
            )
        }
    )
}
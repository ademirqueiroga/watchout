@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

package com.admqueiroga.watchout

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.admqueiroga.common.compose.theme.WatchOutTheme

@Composable
internal fun Home() {
    val navController = rememberNavController()
    Box {
        AppNavigation(navController = navController)
//        Row(
//            modifier = Modifier
//                .padding(horizontal = 32.dp, vertical = 8.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
//        ) {
//            val currentRoute by navController.currentBackStackEntryAsState()
//            for (tab in HomeTabs) {
//                val interactionSource = remember { MutableInteractionSource() }
//                val focused by interactionSource.collectIsFocusedAsState()
//                val isSelected = currentRoute?.destination?.hierarchy?.any {
//                    it.route == tab.screen.route
//                } ?: false
//                Chip(
//                    interactionSource = interactionSource,
//                    colors = ChipDefaults.chipColors(
//                        backgroundColor = if (isSelected) Color.White.copy(0.8f) else Color.Transparent
//                    ),
//                    modifier = Modifier
//                        .onFocusChanged {
//                            if (it.hasFocus) {
//                                navController.navigate(tab.screen.route) {
//                                    launchSingleTop = true
//                                    restoreState = true
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                }
//                            }
//                        },
//                    onClick = {
//                    },
//                ) {
//                    Text(
//                        text = stringResource(id = tab.labelRes),
//                        fontSize = 24.sp,
//                        color = if (isSelected) Color.Black else Color.White.copy(0.6f),
//                    )
//                }
//            }
//        }
    }
}

@Preview(showBackground = true, device = Devices.DESKTOP)
@Composable
fun HomePreview() {
    WatchOutTheme {
        Home()
    }
}
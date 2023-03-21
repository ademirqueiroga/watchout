package com.admqueiroga.watchout

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.admqueiroga.common.compose.ui.BottomNavItem

@Composable
internal fun Home() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentRoute by navController.currentBackStackEntryAsState()
            HomeBottomBar(currentRoute?.destination) { selectedTab ->
                navController.navigate(selectedTab.screen.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavigation(navController, Modifier.padding(paddingValues))
    }
}

@Composable
internal fun HomeBottomBar(
    currentDestination: NavDestination?,
    onTabSelected: (Tab) -> Unit,
) {
    AnimatedVisibility(
        visible = HomeTabs.any { currentDestination?.route?.endsWith(it.screen.route) == true },
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it }
    ) {
        BottomAppBar(
            backgroundColor = MaterialTheme.colors.primarySurface.copy(alpha = 0.5f),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        ) {
            HomeTabs.forEach { tab ->
                BottomNavItem(
                    labelRes = tab.labelRes,
                    iconRes = tab.iconRes,
                    selected = currentDestination?.hierarchy?.any { it.route == tab.screen.route }
                        ?: false,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}
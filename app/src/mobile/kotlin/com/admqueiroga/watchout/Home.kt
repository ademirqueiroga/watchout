package com.admqueiroga.watchout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
internal fun Home() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentRoute by navController.currentBackStackEntryAsState()
            HomeNavigationBar(currentRoute?.destination) { selectedTab ->
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
        AppNavigation(navController, LocalContext.current, Modifier.padding(paddingValues))
    }
}

@Composable
internal fun HomeNavigationBar(
    currentDestination: NavDestination?,
    onTabSelected: (Tab) -> Unit,
) {
    AnimatedVisibility(
        visible = HomeTabs.any { currentDestination?.route?.endsWith(it.screen.route) == true },
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it }
    ) {
        NavigationBar {
            HomeTabs.forEach { tab ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == tab.screen.route
                } ?: false
                NavigationBarItem(
                    selected = selected,
                    onClick = { onTabSelected(tab) },
                    label = { Text(stringResource(id = tab.labelRes)) },
                    icon = {
                        Image(
                            painter = painterResource(id = tab.iconRes),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(LocalContentColor.current)
                        )
                    }
                )
            }
        }
    }
}
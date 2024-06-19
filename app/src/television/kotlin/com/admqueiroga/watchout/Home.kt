@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

package com.admqueiroga.watchout

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
internal fun NavigationRow(@DrawableRes icon: Int, title: String, drawerValue: DrawerValue) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    Row(
        modifier = Modifier
            .background(
                if (focused) Color.White.copy(0.4f) else Color.Transparent,
                shape = CircleShape
            )
            .then(if (drawerValue == DrawerValue.Open) Modifier.width(200.dp) else Modifier.wrapContentWidth())
            .focusable(true, interactionSource)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painter = painterResource(id = icon), contentDescription = "", Modifier.size(32.dp))
        AnimatedVisibility(visible = drawerValue == DrawerValue.Open) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                softWrap = false,
                modifier = Modifier.wrapContentWidth(),
                textAlign = TextAlign.Center,
                fontSize = if (focused) 22.sp else 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
internal fun Home() {
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current
    val (r1, r2, r3) = FocusRequester.createRefs()
    NavigationDrawer(
        drawerState = DrawerState(DrawerValue.Open),
        drawerContent = {
            Column(
                Modifier
                    .background(Color.Gray)
                    .padding(10.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                NavigationRow(
                    R.drawable.home_movies_icon,
                    title = stringResource(id = R.string.home_movies_tab_text),
                    it
                )
                NavigationRow(
                    R.drawable.home_tv_shows_icon,
                    title = stringResource(id = R.string.home_tv_shows_tab_text),
                    it
                )
                NavigationRow(
                    R.drawable.home_people_icon,
                    title = stringResource(id = R.string.home_people_tab_text),
                    it
                )
            }
        }) {
        AppNavigation(navController = navController)
    }
}

//@Preview(showBackground = true, device = Devices.DESKTOP)
//@Composable
//fun HomePreview() {
//    WatchOutTheme {
//        Home()
//    }
//}
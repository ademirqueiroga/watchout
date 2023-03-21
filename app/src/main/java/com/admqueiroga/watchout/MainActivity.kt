package com.admqueiroga.watchout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.data.local.MovieDb
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WatchOutTheme(darkTheme = true) {
                Home()
            }
        }
    }

}


package com.admqueiroga.watchout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.data.local.MovieDb
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchOutTheme(darkTheme = true) {
                Home()
            }
        }

        lifecycleScope.launch {
            try {
                MovieDb.getInstance(this@MainActivity).movieGenreDao().get(0L)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        val requestToken = intent.data?.getQueryParameter("request_token")
        val success = intent.data?.getQueryParameter("success") == "true"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

}


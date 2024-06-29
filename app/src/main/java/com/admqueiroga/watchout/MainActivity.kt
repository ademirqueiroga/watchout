package com.admqueiroga.watchout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.admqueiroga.common.compose.theme.WatchOutTheme
import com.admqueiroga.data.SessionManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchOutTheme(darkTheme = true) {
                Home()
            }
        }

        val requestToken = intent.data?.getQueryParameter("request_token")
        val success = intent.data?.getQueryParameter("approved") == "true"
        if (success && !requestToken.isNullOrBlank()) {
            lifecycleScope.launch {
                SessionManager.writeToken(requestToken)
            }
        }
    }

}


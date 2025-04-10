package com.immortalidiot.geochat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.immortalidiot.geochat.ui.theme.GeoChatTheme
import navigation.AuthNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoChatTheme {
                AuthNavigation()
            }
        }
    }
}

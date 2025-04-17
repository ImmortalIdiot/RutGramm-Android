package com.immortalidiot.geochat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.immortalidiot.geochat.ui.ChatScreenPager
import com.immortalidiot.geochat.ui.theme.GeoChatTheme
import ui.screens.chat.ChatPreviewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GeoChatTheme {
                Navigator(ChatPreviewScreen) {
                    ChatScreenPager()
                }
            }
        }
    }
}

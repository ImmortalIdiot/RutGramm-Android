package com.immortalidiot.geochat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.immortalidiot.geochat.ui.theme.GeoChatTheme
import ui.components.ChatBottomNavigationBar
import ui.screens.chat.ChatPreviewScreen
import ui.screens.news.NewsScreen
import ui.screens.profile.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GeoChatTheme {
                val screens = listOf(NewsScreen, ChatPreviewScreen, ProfileScreen)
                var selectedTab by rememberSaveable { mutableIntStateOf(1) }
                val currentScreen = screens[selectedTab]

                Navigator(currentScreen) { navigator ->
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            ChatBottomNavigationBar(
                                selectedIndex = selectedTab,
                                onItemSelected = { index ->
                                    selectedTab = index
                                    navigator.replace(screens[index])
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            CurrentScreen()
                        }
                    }
                }
            }
        }
    }
}

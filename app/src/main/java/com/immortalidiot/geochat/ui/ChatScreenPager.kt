package com.immortalidiot.geochat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ui.components.ChatBottomNavigationBar
import ui.screens.chat.ChatPreviewScreen
import ui.screens.news.NewsScreen
import ui.screens.profile.ProfileScreen

@Composable
fun ChatScreenPager() {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }
    val scope = rememberCoroutineScope()

    val screens: List<@Composable () -> Unit> = listOf(
        { NewsScreen.Content() },
        { ChatPreviewScreen.Content() },
        { ProfileScreen.Content() }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            ChatBottomNavigationBar(
                selectedIndex = pagerState.currentPage,
                onItemSelected = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) { page ->
            screens[page].invoke()
        }
    }
}

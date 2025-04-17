package ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import data.mock.chats
import ui.components.ChatPreviewList
import ui.components.ChatTopBar

object ChatPreviewScreen : Screen {
    private fun readResolve(): Any = ChatPreviewScreen

    @Composable
    override fun Content() {
        ChatPreviewScreenComposable()
    }
}

@Composable
fun ChatPreviewScreenComposable() {
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChatTopBar(
            query = query,
            expanded = expanded,
            onQueryChange = { query = it },
            onExpandedChange = { expanded = it }
        )
        ChatPreviewList(
            modifier = Modifier,
            chats = chats,
            onChatPreviewClick = {}
        )
    }
}

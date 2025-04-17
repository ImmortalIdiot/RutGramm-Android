package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.mock.chats
import data.model.ChatPreviewModel

@Composable
internal fun ChatPreviewList(
    chats: List<ChatPreviewModel>,
    onChatPreviewClick: (ChatPreviewModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(chats) { index, chat ->
            Column(
                modifier = Modifier.clickable {
                    onChatPreviewClick(chat)
                }
            ) {
                ChatPreview(
                    nickname = chat.nickname,
                    lastMessage = chat.lastMessage,
                    countUnreadMessages = chat.countUnreadMessages,
                    avatarUrl = chat.avatarUrl,
                )

                if (index < chats.size - 1) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ChatPreviewList(
        chats = chats,
        onChatPreviewClick = {}
    )
}

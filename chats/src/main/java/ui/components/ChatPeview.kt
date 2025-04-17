package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.mock.chats

@Composable
internal fun ChatPreview(
    nickname: String,
    lastMessage: String = "",
    countUnreadMessages: Int = 0,
    avatar: ImageBitmap? = null,
) {
    ChatPreviewContent(
        nickname = nickname,
        lastMessage = lastMessage,
        countUnreadMessages = countUnreadMessages,
        avatar = {
            if (avatar != null) {
                Image(
                    bitmap = avatar,
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                DefaultAvatar(nickname)
            }
        }
    )
}

@Composable
internal fun ChatPreview(
    nickname: String,
    lastMessage: String = "",
    countUnreadMessages: Int = 0,
    avatarUrl: String?,
) {
    ChatPreviewContent(
        nickname = nickname,
        lastMessage = lastMessage,
        countUnreadMessages = countUnreadMessages,
        avatar = {
            if (!avatarUrl.isNullOrBlank()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                DefaultAvatar(nickname)
            }
        }
    )
}


@Composable
private fun ChatPreviewContent(
    nickname: String,
    lastMessage: String,
    countUnreadMessages: Int,
    avatar: @Composable () -> Unit,
) {

    val badgeColor = Color(144, 213, 255)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        avatar()

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = nickname,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (lastMessage.isNotBlank()) {
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (countUnreadMessages > 0) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = badgeColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = countUnreadMessages.toString(),
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Preview chat with short title, short last message and without unread messages
 */
@Composable
@Preview
private fun First() {
    ChatPreview(
        chats[0].nickname,
        chats[0].lastMessage,
        chats[0].countUnreadMessages,
    )
}

/**
 * Preview chat with short title, short last message and with unread messages
 */
@Composable
@Preview
private fun Second() {
    ChatPreview(
        chats[1].nickname,
        chats[1].lastMessage,
        chats[1].countUnreadMessages,
    )
}

/**
 * Preview chat with short title, long last message and without unread messages
 */
@Composable
@Preview
private fun Third() {
    ChatPreview(
        chats[2].nickname,
        chats[2].lastMessage,
        chats[2].countUnreadMessages,
    )
}

/**
 * Preview chat with short title, long last message and with unread messages
 */
@Composable
@Preview
private fun Fourth() {
    ChatPreview(
        chats[3].nickname,
        chats[3].lastMessage,
        chats[3].countUnreadMessages,
        chats[3].avatarUrl
    )
}

/**
 * Preview chat with long title, short last message and without unread messages
 */
@Composable
@Preview
private fun Fifth() {
    ChatPreview(
        chats[4].nickname,
        chats[4].lastMessage,
        chats[4].countUnreadMessages,
    )
}

/**
 * Preview chat with long title, short last message and with unread messages
 */
@Composable
@Preview
private fun Sixth() {
    ChatPreview(
        chats[5].nickname,
        chats[5].lastMessage,
        chats[5].countUnreadMessages,
    )
}

/**
 * Preview chat with long title, long last message and without unread messages
 */
@Composable
@Preview
private fun Seventh() {
    ChatPreview(
        chats[6].nickname,
        chats[6].lastMessage,
        chats[6].countUnreadMessages,
    )
}

/**
 * Preview chat with long title, long last message and with unread messages
 */
@Composable
@Preview
private fun Eighth() {
    ChatPreview(
        chats[7].nickname,
        chats[7].lastMessage,
        chats[7].countUnreadMessages,
    )
}

@Preview
@Composable
private fun PreviewWithImageBitmapAvatar() {
    val bitmap = ImageBitmap(48, 48)

    ChatPreview(
        nickname = "BitmapUser",
        lastMessage = "Avatar from ImageBitmap",
        countUnreadMessages = 2,
        avatar = bitmap
    )
}

@Preview
@Composable
private fun PreviewWithUrlAvatar() {
    ChatPreview(
        nickname = "User url",
        lastMessage = "Avatar from URL",
        countUnreadMessages = 1,
        avatarUrl = "https://i.pinimg.com/736x/2b/47/18/2b4718827a37b6f11dc82e939984c571.jpg"
    )
}



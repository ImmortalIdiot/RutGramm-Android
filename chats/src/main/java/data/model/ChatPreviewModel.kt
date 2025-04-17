package data.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class ChatPreviewModel(
    val id: Int,
    val nickname: String,
    val lastMessage: String = "",
    val countUnreadMessages: Int = 0,
    val avatarUrl: String? = null
)

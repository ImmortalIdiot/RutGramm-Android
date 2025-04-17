package di

import cafe.adriel.voyager.core.registry.screenModule
import navigation.ApplicationNavigation
import ui.screens.chat.ChatPreviewScreen
import ui.screens.news.NewsScreen
import ui.screens.profile.ProfileScreen

val chatsScreenModule = screenModule {
    register<ApplicationNavigation.News> { NewsScreen }
    register<ApplicationNavigation.Chats> { ChatPreviewScreen }
    register<ApplicationNavigation.Profile> { ProfileScreen }
}

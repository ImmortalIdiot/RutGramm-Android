package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.chats.R
import ui.screens.chat.ChatPreviewScreen
import ui.screens.news.NewsScreen
import ui.screens.profile.ProfileScreen

@Composable
fun ChatBottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = {
                onItemSelected(0)
                navigator push NewsScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Article,
                    contentDescription = ""
                )
            },
            label = { Text(text = stringResource(R.string.news)) }
        )

        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {
                onItemSelected(1)
                navigator push ChatPreviewScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Forum,
                    contentDescription = ""
                )
            },
            label = { Text(text = stringResource(R.string.chats)) }
        )

        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = {
                onItemSelected(2)
                navigator push ProfileScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = ""
                )
            },
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.immortalidiot.chats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    query: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onExpandedChange: (Boolean) -> Unit = {},
    onAdd: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        SearchBar(
            modifier = modifier,
            inputField = {
                val searching = expanded || query.isNotEmpty()

                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {
                        onExpandedChange(false)
                        onSearch(it)
                    },
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = { Text(text = stringResource(R.string.search_bar)) },
                    trailingIcon = {
                        Row {
                            AnimatedVisibility(
                                visible = !searching,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(
                                    onClick = { onExpandedChange(true) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = ""
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    if (searching) {
                                        onQueryChange("")
                                        onExpandedChange(false)
                                    } else {
                                        onAdd()
                                    }
                                }
                            ) {
                                Crossfade(targetState = searching) {
                                    if (it) {
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = ""
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {}
    }
}

@Preview
@Composable
private fun Preview() {
    ChatTopBar(
        query = "Test query",
        expanded = false
    )
}

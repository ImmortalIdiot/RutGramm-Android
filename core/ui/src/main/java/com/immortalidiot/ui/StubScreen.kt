package com.immortalidiot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun StubScreen(
    modifier: Modifier = Modifier,
    screenText: String = "Stub screen",
    firstButtonText: String = "First button",
    firstButtonOnClick: () -> Unit = {},
    secondButtonText: String = "Second button",
    secondButtonOnClick: () -> Unit = {},
) {
    val topPadding = 24.dp
    val paddingBetweenButtons = 32.dp
    val buttonWidth = 160.dp

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            modifier = modifier.padding(top = topPadding),
            text = screenText,
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            modifier = modifier.align(Alignment.Center),
        ) {
            if (firstButtonText.isNotEmpty()) {
                Button(
                    modifier = Modifier.width(buttonWidth),
                    onClick = firstButtonOnClick
                ) {
                    Text(
                        text = firstButtonText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(paddingBetweenButtons))

            if (secondButtonText.isNotEmpty()) {
                Button(
                    modifier = Modifier.width(buttonWidth),
                    onClick = secondButtonOnClick
                ) {
                    Text(
                        text = secondButtonText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

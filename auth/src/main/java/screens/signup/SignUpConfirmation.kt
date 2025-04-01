package screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.auth.R
import components.bars.LocalSnackbarHostState
import components.bars.TopSnackbar
import components.bars.showMessage
import org.koin.androidx.compose.koinViewModel

internal class SignUpConfirmationScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: SignUpConfirmationViewModel = koinViewModel()
        SignUpConfirmation(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
private fun SignUpConfirmation(
    viewModel: SignUpConfirmationViewModel,
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    val context = LocalContext.current

    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val code by viewModel.code.collectAsState()

    val focusRequester = List(6) { FocusRequester() }

    LaunchedEffect(uiState) {
        if (uiState is SignUpConfirmationUiState.Error) {
            val snackbarMessage = (uiState as SignUpConfirmationUiState.Error).message
            snackbarHostState.showMessage(message = snackbarMessage)
        }

        if (uiState is SignUpConfirmationUiState.Success) {
            snackbarHostState.showMessage(message = context.getString(R.string.successful_signup))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = 64.dp,
                horizontal = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        if (uiState is SignUpConfirmationUiState.Error) {
            TopSnackbar(
                snackbarHostState = snackbarHostState,
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }

        if (uiState is SignUpConfirmationUiState.Success) {
            TopSnackbar(snackbarHostState = snackbarHostState)
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = context.getString(R.string.confirm_signup),
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            for (i in 0 until 6) {
                OutlinedTextField(
                    value = if (i < code.length) code[i].toString() else "",
                    onValueChange = { newChar ->
                        if (newChar.length == 1 && newChar.all { it.isDigit() }) {
                            val newCode = StringBuilder(code).apply {
                                if (i < length) {
                                    setCharAt(i, newChar.first())
                                } else {
                                    append(newChar)
                                }
                            }.toString()

                            viewModel updateCode newCode

                            if (i < 5) {
                                focusRequester[i + 1].requestFocus()
                            }
                        } else if (newChar.isEmpty()) {
                            val newCode = if (code.isNotEmpty() && i < code.length) {
                                code.removeRange(i, i + 1)
                            } else {
                                code
                            }

                            viewModel updateCode newCode

                            if (i > 0) {
                                focusRequester[i - 1].requestFocus()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .width(48.dp)
                        .height(56.dp)
                        .focusRequester(focusRequester[i]),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(208.dp)
                    .height(48.dp),
                onClick = { viewModel verifyCode code },
                enabled = code.length == 6,
            ) {
                Text(
                    text = context.getString(R.string.confirm),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier.clickable { navigator push SignUpScreen(modifier = modifier) },
                text = context.getString(R.string.go_back),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

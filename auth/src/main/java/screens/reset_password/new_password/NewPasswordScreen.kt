package screens.reset_password.new_password

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.auth.R
import components.bars.LocalSnackbarHostState
import components.bars.TopSnackbar
import components.bars.showMessage
import org.koin.androidx.compose.koinViewModel
import screens.login.LoginScreen
import screens.reset_password.otp.OtpScreen

internal class NewPasswordScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: NewPasswordScreenViewModel = koinViewModel()

        NewPasswordScreenComposable(
            modifier = modifier,
            viewModel = viewModel
        )
    }
}

@Composable
private fun NewPasswordScreenComposable(
    modifier: Modifier,
    viewModel: NewPasswordScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow

    val context = LocalContext.current
    val activity = context as Activity

    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

    LaunchedEffect(uiState) {
        if (uiState is NewPasswordScreenUiState.Init) {
            snackbarHostState.showMessage(context.getString(R.string.input_new_password))
            viewModel.updateUiState()
        }

        if (uiState is NewPasswordScreenUiState.Error) {
            val snackbarMessage = (uiState as NewPasswordScreenUiState.Error).errorMessage
            snackbarHostState.showMessage(message = snackbarMessage)
        }

        if (uiState is NewPasswordScreenUiState.Success) {
            snackbarHostState.showMessage(message = context.getString(R.string.password_changed))
            navigator push LoginScreen(modifier = modifier)
        }
    }

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        if (uiState is NewPasswordScreenUiState.Error) {
            TopSnackbar(
                snackbarHostState = snackbarHostState,
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }

        if (
            uiState is NewPasswordScreenUiState.Init ||
            uiState is NewPasswordScreenUiState.Success
        ) {
            TopSnackbar(snackbarHostState = snackbarHostState)
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = context.getString(R.string.reset_password_screen),
            style = MaterialTheme.typography.headlineMedium
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier,
                value = email,
                onValueChange = { newEmail ->
                    viewModel changeEmail newEmail
                },
                label = {
                    Text(text = context.getString(R.string.email_field))
                },
                maxLines = 1,
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            OutlinedTextField(
                modifier = Modifier,
                value = password,
                onValueChange = { newPassword ->
                    viewModel changePassword newPassword
                },
                label = {
                    Text(text = context.getString(R.string.password_field))
                },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.changePasswordVisibility()
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Rounded.Visibility
                            } else {
                                Icons.Rounded.VisibilityOff
                            },
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = visualTransformation,
                maxLines = 1,
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            OutlinedTextField(
                modifier = Modifier,
                value = confirmPassword,
                onValueChange = { newConfirmPassword ->
                    viewModel changeConfirmationPassword newConfirmPassword
                },
                label = {
                    Text(text = context.getString(R.string.confirm_password_field))
                },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.changePasswordVisibility()
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Rounded.Visibility
                            } else {
                                Icons.Rounded.VisibilityOff
                            },
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = visualTransformation,
                maxLines = 1,
                singleLine = true,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(296.dp)
                    .height(48.dp),
                onClick = {
                    viewModel.resetPassword(
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                }
            ) {
                Text(
                    text = context.getString(R.string.reset),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Text(
                modifier = Modifier.clickable {
                    navigator push OtpScreen(modifier = modifier)
                },
                text = context.getString(R.string.go_back),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
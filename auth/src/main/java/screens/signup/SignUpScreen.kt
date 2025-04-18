package screens.signup

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

internal object SignUpScreen : Screen {
    private fun readResolve(): Any = SignUpScreen

    @Composable
    override fun Content() {
        val viewModel: SignUpScreenViewModel = koinViewModel()

        SignUpScreenComposable(viewModel = viewModel)
    }
}

@Composable
private fun SignUpScreenComposable(viewModel: SignUpScreenViewModel) {
    val navigator = LocalNavigator.currentOrThrow

    val context = LocalContext.current
    val activity = context as Activity

    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val login by viewModel.login.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

    LaunchedEffect(uiState) {
        if (uiState is SignUpScreenUiState.Error) {
            val snackbarMessage = (uiState as SignUpScreenUiState.Error).errorMessage
            snackbarHostState.showMessage(message = snackbarMessage)
        }

        if (uiState is SignUpScreenUiState.Success) {
            snackbarHostState.showMessage(message = context.getString(R.string.check_email))
            navigator push SignUpConfirmationScreen
        }
    }

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        if (uiState is SignUpScreenUiState.Error) {
            TopSnackbar(
                snackbarHostState = snackbarHostState,
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }

        if (uiState is SignUpScreenUiState.Success) {
            TopSnackbar(snackbarHostState = snackbarHostState)
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = context.getString(R.string.sign_up_screen),
            style = MaterialTheme.typography.headlineMedium
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier,
                value = login,
                onValueChange = { newLogin ->
                    viewModel changeLogin newLogin
                },
                label = {
                    Text(text = context.getString(R.string.login_field))
                },
                maxLines = 1,
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

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
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(296.dp)
                    .height(48.dp),
                onClick = {
                    viewModel.register(
                        login = login,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                }
            ) {
                Text(
                    text = context.getString(R.string.sign_up),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier.clickable { navigator push LoginScreen },
                text = context.getString(R.string.to_sign_in),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

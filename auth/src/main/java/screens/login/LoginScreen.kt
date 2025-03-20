package screens.login

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.auth.R
import components.bars.LocalSnackbarHostState
import components.bars.TopSnackbar
import components.bars.showMessage
import screens.reset_password.email.ResetPasswordScreen
import screens.signup.SignUpScreen

internal class LoginScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginScreenViewModel = viewModel()
        LoginScreenComposable(modifier = modifier, viewModel = viewModel)
    }
}

@Composable
private fun LoginScreenComposable(
    modifier: Modifier,
    viewModel: LoginScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val activity = context as Activity
    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val login by viewModel.login.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    val toAnotherScreenStyle = MaterialTheme.typography.bodyLarge

    LaunchedEffect(uiState) {
        if (uiState is LoginScreenUiState.Error) {
            val snackbarMessage = (uiState as LoginScreenUiState.Error).errorMessage
            snackbarHostState.showMessage(message = snackbarMessage)
        }

        if (uiState is LoginScreenUiState.Success) {
            snackbarHostState.showMessage(context.getString(R.string.successful_login))
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
        if (uiState is LoginScreenUiState.Error) {
            TopSnackbar(
                snackbarHostState = snackbarHostState,
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }

        if (uiState is LoginScreenUiState.Success) {
            TopSnackbar(snackbarHostState = snackbarHostState)
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = context.getString(R.string.sign_in_screen),
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
                    viewModel.changeLogin(newValue = newLogin)
                },
                label = {
                    Text(text = context.getString(R.string.login_or_email_field))
                },
                maxLines = 1,
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            OutlinedTextField(
                modifier = Modifier,
                value = password,
                onValueChange = { newPassword ->
                    viewModel.changePassword(newValue = newPassword)
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
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navigator.push(ResetPasswordScreen(modifier = modifier)) },
                text = context.getString(R.string.to_reset_password),
                style = toAnotherScreenStyle
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(160.dp)
                    .height(48.dp),
                onClick = {
                    viewModel.login(
                        login = login,
                        password = password,
                        context = context
                    )
                }
            ) {
                Text(
                    text = context.getString(R.string.sign_in),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier.clickable {
                    navigator push SignUpScreen(modifier = modifier)
                },
                text = context.getString(R.string.to_register),
                style = toAnotherScreenStyle
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoginScreenPreview() {
    LoginScreenComposable(modifier = Modifier.fillMaxSize(), viewModel = viewModel())
}

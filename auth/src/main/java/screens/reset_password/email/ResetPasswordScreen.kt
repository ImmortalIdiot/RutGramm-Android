package screens.reset_password.email

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
import androidx.compose.material3.Button
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

internal class ResetPasswordScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ResetPasswordScreenViewModel = koinViewModel()

        ResetPasswordScreenComposable(
            modifier = modifier,
            viewModel = viewModel
        )
    }
}

@Composable
private fun ResetPasswordScreenComposable(
    modifier: Modifier,
    viewModel: ResetPasswordScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow

    val context = LocalContext.current
    val activity = context as Activity

    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is ResetPasswordScreenUiState.Success) {
            navigator push OtpScreen(modifier = modifier)
            viewModel.resetUiState()
        }

        if (uiState is ResetPasswordScreenUiState.Error) {
            val message = (uiState as ResetPasswordScreenUiState.Error).error
            snackbarHostState.showMessage(message = message)
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
        if (uiState is ResetPasswordScreenUiState.Error) {
            TopSnackbar(
                snackbarHostState = snackbarHostState,
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }

        if (uiState is ResetPasswordScreenUiState.Success) {
            TopSnackbar(snackbarHostState = snackbarHostState)
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = context.getString(R.string.reset_password_screen),
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = email,
            onValueChange = { newEmail ->
                viewModel changeEmail newEmail
            },
            label = {
                Text(text = context.getString(R.string.email_field))
            },
            maxLines = 1,
            singleLine = true
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(240.dp)
                    .height(48.dp),
                onClick = { viewModel sendEmail email }
            ) {
                Text(
                    text = context.getString(R.string.send_code),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier.clickable {
                    navigator push LoginScreen(modifier = modifier)
                },
                text = context.getString(R.string.go_back),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

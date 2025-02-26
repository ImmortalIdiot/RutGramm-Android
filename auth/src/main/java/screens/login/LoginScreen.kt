package screens.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.ui.StubScreen
import screens.registration.RegistrationScreen
import screens.reset_password.ResetPasswordScreen

internal class LoginScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        LoginScreenComposable(modifier = modifier)
    }
}

@Composable
private fun LoginScreenComposable(
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    StubScreen(
        modifier = modifier,
        screenText = "Login screen",
        firstButtonText = "Sign up",
        firstButtonOnClick = { navigator push RegistrationScreen(modifier = modifier) },
        secondButtonText = "To reset password",
        secondButtonOnClick = { navigator push ResetPasswordScreen(modifier = modifier) }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoginScreenPreview() {
    LoginScreenComposable(modifier = Modifier.fillMaxSize())
}

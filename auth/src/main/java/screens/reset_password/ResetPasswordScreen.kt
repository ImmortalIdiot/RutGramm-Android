package screens.reset_password

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.ui.StubScreen
import screens.login.LoginScreen

internal class ResetPasswordScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        ResetPasswordScreenComposable(modifier = modifier)
    }
}

@Composable
private fun ResetPasswordScreenComposable(
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    StubScreen(
        modifier = modifier,
        screenText = "Reset password screen",
        firstButtonText = "Sign in",
        firstButtonOnClick = { navigator push LoginScreen(modifier = modifier) },
        secondButtonText = ""
    )
}

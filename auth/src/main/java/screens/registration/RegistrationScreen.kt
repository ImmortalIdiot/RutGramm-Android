package screens.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.immortalidiot.ui.StubScreen
import screens.login.LoginScreen

internal class RegistrationScreen(
    private val modifier: Modifier
) : Screen {
    @Composable
    override fun Content() {
        RegistrationScreenComposable(modifier = modifier)
    }
}

@Composable
private fun RegistrationScreenComposable(
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow

    StubScreen(
        modifier = modifier,
        screenText = "Registration screen",
        firstButtonText = "Sign in",
        firstButtonOnClick = { navigator push LoginScreen(modifier = modifier) },
        secondButtonText = ""
    )
}

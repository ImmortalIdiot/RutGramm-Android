package ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.immortalidiot.ui.StubScreen

object ProfileScreen : Screen {
    private fun readResolve(): Any = ProfileScreen

    @Composable
    override fun Content() {
        StubScreen(
            screenText = "Profile screen",
            firstButtonText = "",
            secondButtonText = "",
        )
    }
}

package ui.screens.news

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.immortalidiot.ui.StubScreen

object NewsScreen : Screen {
    private fun readResolve(): Any = NewsScreen

    @Composable
    override fun Content() {
        StubScreen(
            screenText = "News screen",
            firstButtonText = "",
            secondButtonText = "",
        )
    }
}

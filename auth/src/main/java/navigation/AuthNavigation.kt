package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import screens.login.LoginScreen

@Composable
fun AuthNavigation() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Navigator(LoginScreen(modifier = Modifier.padding(paddingValues = innerPadding)))
    }
}

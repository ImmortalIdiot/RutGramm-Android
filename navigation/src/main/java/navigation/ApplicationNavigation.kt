package navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class ApplicationNavigation : ScreenProvider {
    data object Login : ApplicationNavigation()

    data object SignUp : ApplicationNavigation()
    data object SignUpConfirmation : ApplicationNavigation()

    data object ResetPassword : ApplicationNavigation()
    data object ResetPasswordOtp : ApplicationNavigation()
    data object NewPassword : ApplicationNavigation()

    /**
     * These objects belong to the chats module
     */
    data object News : ApplicationNavigation()
    data object Chats : ApplicationNavigation()
    data object Profile : ApplicationNavigation()
}

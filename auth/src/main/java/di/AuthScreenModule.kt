package di

import cafe.adriel.voyager.core.registry.screenModule
import navigation.ApplicationNavigation
import screens.login.LoginScreen
import screens.reset_password.email.ResetPasswordScreen
import screens.reset_password.new_password.NewPasswordScreen
import screens.reset_password.otp.OtpScreen
import screens.signup.SignUpConfirmationScreen
import screens.signup.SignUpScreen

val authScreenModule = screenModule {
    register<ApplicationNavigation.Login> { LoginScreen }

    register<ApplicationNavigation.SignUp> { SignUpScreen }
    register<ApplicationNavigation.SignUpConfirmation> { SignUpConfirmationScreen }

    register<ApplicationNavigation.ResetPassword> { ResetPasswordScreen }
    register<ApplicationNavigation.ResetPasswordOtp> { OtpScreen }
    register<ApplicationNavigation.NewPassword> { NewPasswordScreen }
}

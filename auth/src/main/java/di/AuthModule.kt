package di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import screens.login.LoginScreenViewModel
import screens.reset_password.email.ResetPasswordScreenViewModel
import screens.reset_password.new_password.NewPasswordScreenViewModel
import screens.reset_password.otp.OtpScreenViewModel
import screens.signup.SignUpConfirmationViewModel
import screens.signup.SignUpScreenViewModel

val authModule = module {
    single<Context> { androidApplication() }

    viewModel { LoginScreenViewModel(get()) }

    viewModel { SignUpScreenViewModel(get()) }
    viewModel { SignUpConfirmationViewModel(get()) }

    viewModel { ResetPasswordScreenViewModel(get()) }
    viewModel { OtpScreenViewModel(get()) }
    viewModel { NewPasswordScreenViewModel(get()) }
}

package di

import domain.AuthDataStore
import domain.AuthDataStoreImpl
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
    single<ResourceProvider> { ResourceProviderImpl(androidApplication()) }
    single<AuthDataStore> { AuthDataStoreImpl(androidApplication()) }

    viewModel { LoginScreenViewModel(get(), get()) }

    viewModel { SignUpScreenViewModel(get(), get()) }
    viewModel { SignUpConfirmationViewModel(get(), get()) }

    viewModel { ResetPasswordScreenViewModel(get(), get()) }
    viewModel { OtpScreenViewModel(get(), get()) }
    viewModel { NewPasswordScreenViewModel(get(), get()) }
}

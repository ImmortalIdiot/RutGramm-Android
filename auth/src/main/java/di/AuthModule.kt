package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import screens.login.LoginScreenViewModel
import screens.signup.SignUpConfirmationViewModel
import screens.signup.SignUpScreenViewModel

val authModule = module {
    viewModel { LoginScreenViewModel() }
    viewModel { SignUpScreenViewModel() }
    viewModel { SignUpConfirmationViewModel() }
}

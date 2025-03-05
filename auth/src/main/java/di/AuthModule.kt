package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import screens.login.LoginScreenViewModel

val authModule = module {
    viewModel { LoginScreenViewModel() }
}

package screens.signup

import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import di.ResourceProvider
import domain.AuthDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignUpScreenViewModel(
    private val resourceProvider: ResourceProvider,
    private val datastore: AuthDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow<SignUpScreenUiState>(SignUpScreenUiState.Init)
    val uiState: StateFlow<SignUpScreenUiState> = _uiState.asStateFlow()

    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    infix fun changeLogin(newLogin: String) {
        _login.value = newLogin
    }

    infix fun changeEmail(newEmail: String) {
        _email.value = newEmail
    }

    infix fun changePassword(newPassword: String) {
        _password.value = newPassword
    }

    infix fun changeConfirmationPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun changePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun register(
        login: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _uiState.value = SignUpScreenUiState.Loading

            if (!validateInputs(
                    login = login,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            ) {
                return@launch
            }

            viewModelScope.launch {
                val result = network.registration.register(login, email, password)

                _uiState.value = when {
                    result.isSuccess -> {
                        datastore.saveUserId(result.getOrNull()!!.userId)
                        SignUpScreenUiState.Success
                    }

                    else -> SignUpScreenUiState.Error(resourceProvider.getString(R.string.server_error))
                }
            }
        }
    }

    private fun validateInputs(
        login: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val errorMessage = when {
            login.isEmpty() -> resourceProvider.getString(R.string.empty_login)
            email.isEmpty() -> resourceProvider.getString(R.string.empty_email)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> resourceProvider.getString(R.string.invalid_email)
            password.isEmpty() -> resourceProvider.getString(R.string.empty_password)
            password != confirmPassword -> resourceProvider.getString(R.string.confirm_password_error)
            else -> null
        }

        return if (errorMessage == null) {
            true
        } else {
            errorMessage.let { message ->
                viewModelScope.launch {
                    _uiState.value = SignUpScreenUiState.Error(message)
                    resetUiState()
                }
            }

            false
        }
    }

    private suspend fun resetUiState() {
        delay(4000L)
        _uiState.update { SignUpScreenUiState.Init }
    }
}

@Immutable
internal sealed interface SignUpScreenUiState {
    data object Init : SignUpScreenUiState
    data object Loading : SignUpScreenUiState
    data object Success : SignUpScreenUiState
    data class Error(val errorMessage: String) : SignUpScreenUiState
}

package screens.signup

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignUpScreenViewModel : ViewModel() {
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

    fun changeLogin(newLogin: String) {
        _login.value = newLogin
    }

    fun changeEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun changePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun changeConfirmationPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun changePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun register(
        login: String,
        email: String,
        password: String,
        confirmPassword: String,
        context: Context
    ) {
        viewModelScope.launch {
            _uiState.value = SignUpScreenUiState.Loading
        }

        if (!validateInputs(
            login = login,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            context = context
        )) {
            return
        }

        // TODO: add sign up request

        viewModelScope.launch {
            _uiState.value = SignUpScreenUiState.Success
        }
    }

    private fun validateInputs(
        login: String,
        email: String,
        password: String,
        confirmPassword: String,
        context: Context
    ): Boolean {
        val errorMessage = when {
            login.isEmpty() -> context.getString(R.string.empty_login)
            email.isEmpty() -> context.getString(R.string.empty_email)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> context.getString(R.string.invalid_email)
            password.isEmpty() -> context.getString(R.string.empty_password)
            password != confirmPassword -> context.getString(R.string.confirm_password_error)
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

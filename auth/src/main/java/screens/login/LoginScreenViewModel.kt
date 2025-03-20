package screens.login

import android.app.Application
import androidx.compose.runtime.Immutable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import domain.AuthData
import domain.AuthStore
import domain.models.login.LoginResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import network.login.authenticate

internal class LoginScreenViewModel(application: Application) : AndroidViewModel(application = application) {
    val context = getApplication<Application>()

    private val _uiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.Init)
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    fun changeLogin(newValue: String) {
        _login.value = newValue
    }

    fun changePassword(newValue: String) {
        _password.value = newValue
    }

    fun changePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login(login: String, password: String) {
        _uiState.value = LoginScreenUiState.Loading

        if (!validateInputs(first = login, second = password)) {
            return
        }

        viewModelScope.launch {
            val result: Result<LoginResponse> = authenticate(login, password)

            _uiState.value = when {
                result.isSuccess -> {
                    val authData = AuthData(
                        result.getOrNull()!!.userId,
                        result.getOrNull()!!.accessToken,
                        result.getOrNull()!!.refreshToken
                    )
                    AuthStore.saveAuthData(context = context, data = authData)
                    LoginScreenUiState.Success
                }

                else -> LoginScreenUiState.Error(context.getString(R.string.server_error))
            }
        }
    }

    private fun validateInputs(first: String, second: String): Boolean {
        if (first.isEmpty()) {
            viewModelScope.launch {
                resetUiState()
                _uiState.value = LoginScreenUiState.Error(
                    errorMessage = context.getString(R.string.empty_login)
                )
            }
            return false
        }

        if (second.isEmpty()) {
            viewModelScope.launch {
                resetUiState()
                _uiState.value = LoginScreenUiState.Error(
                    errorMessage = context.getString(R.string.empty_password)
                )
            }
            return false
        }

        return true
    }

    private fun resetUiState() {
        viewModelScope.launch {
            delay(3000L)
            _uiState.value = LoginScreenUiState.Init
        }
    }
}

@Immutable
internal sealed interface LoginScreenUiState {
    data object Init : LoginScreenUiState
    data object Loading : LoginScreenUiState
    data object Success : LoginScreenUiState
    data class Error(val errorMessage: String) : LoginScreenUiState
}
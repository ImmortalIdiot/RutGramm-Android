package screens.reset_password.new_password

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import domain.AuthStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NewPasswordScreenViewModel(application: Application) : AndroidViewModel(application = application) {
    private val context = getApplication<Application>()

    private val _uiState = MutableStateFlow<NewPasswordScreenUiState>(NewPasswordScreenUiState.Init)
    val uiState: StateFlow<NewPasswordScreenUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    init {
        viewModelScope.launch {
            _email.value = AuthStore.loadEmailFromDataStore(context = getApplication())
        }
    }

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

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

    fun updateUiState() {
        _uiState.value = NewPasswordScreenUiState.Initialized
    }

    fun resetPassword(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (isValidInputs(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            ) {
                val result = network.reset_password.resetPassword(
                    email = email,
                    newPassword = password
                )

                if (result.isSuccess && result.getOrNull() == "success") {
                    _uiState.value = NewPasswordScreenUiState.Success
                } else if (result.isSuccess) {
                    _uiState.value = NewPasswordScreenUiState.Error(
                        context.getString(R.string.smth_went_wrong_error)
                    )
                } else {
                    _uiState.value = NewPasswordScreenUiState.Error(
                        context.getString(R.string.server_error)
                    )
                }
            } else {
                return@launch
            }
        }
    }

    private fun isValidInputs(
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        val errorMessage = when {
            email.isEmpty() -> context.getString(R.string.empty_email)
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> context.getString(R.string.invalid_email)

            password.isEmpty() -> context.getString(R.string.empty_password)
            password != confirmPassword -> context.getString(R.string.confirm_password_error)
            else -> null
        }

        return if (errorMessage == null) {
            true
        } else {
            errorMessage.let { message ->
                viewModelScope.launch {
                    _uiState.value = NewPasswordScreenUiState.Error(message)
                    resetUiState()
                }
            }

            false
        }
    }

    private suspend fun resetUiState() {
        delay(4000L)
        _uiState.update { NewPasswordScreenUiState.Initialized }
    }
}

@Immutable
internal sealed interface NewPasswordScreenUiState {
    data object Init : NewPasswordScreenUiState
    data object Initialized : NewPasswordScreenUiState
    data object Success : NewPasswordScreenUiState
    data class Error(val errorMessage: String) : NewPasswordScreenUiState
}
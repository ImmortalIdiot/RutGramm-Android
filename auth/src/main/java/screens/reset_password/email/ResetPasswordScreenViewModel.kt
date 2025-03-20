package screens.reset_password.email

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import domain.AuthStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordScreenViewModel(application: Application) : AndroidViewModel(application = application) {
    private val context = getApplication<Application>()

    private val _uiState =
        MutableStateFlow<ResetPasswordScreenUiState>(ResetPasswordScreenUiState.Init)
    val uiState: StateFlow<ResetPasswordScreenUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    fun changeEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun resetUiState() {
        _uiState.value = ResetPasswordScreenUiState.Init
    }

    infix fun sendEmail(email: String) {
        if (isValidEmail(email = email)) {
            viewModelScope.launch {
                val result = network.reset_password.sendEmail(email)

                if (result.isSuccess && result.getOrNull() == "success") {
                    AuthStore.saveEmailToDataStore(context = context, email = email)
                    _uiState.value = ResetPasswordScreenUiState.Success
                } else if (result.isSuccess) {
                    _uiState.value = ResetPasswordScreenUiState.Error(
                       error = context.getString(R.string.email_not_exists)
                    )
                } else {
                    _uiState.value = ResetPasswordScreenUiState.Error(
                        error = context.getString(R.string.server_error)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _uiState.value =
                    ResetPasswordScreenUiState.Error(context.getString(R.string.invalid_email))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

@Immutable
sealed interface ResetPasswordScreenUiState {
    data object Init : ResetPasswordScreenUiState
    data class Error(val error: String) : ResetPasswordScreenUiState
    data object Success : ResetPasswordScreenUiState
}

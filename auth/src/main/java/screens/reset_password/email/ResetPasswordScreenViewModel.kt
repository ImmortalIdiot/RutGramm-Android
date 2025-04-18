package screens.reset_password.email

import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import di.ResourceProvider
import domain.AuthDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ResetPasswordScreenViewModel(
    private val resourceProvider: ResourceProvider,
    private val dataStore: AuthDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow<ResetPasswordScreenUiState>(ResetPasswordScreenUiState.Init)
    val uiState: StateFlow<ResetPasswordScreenUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    infix fun changeEmail(newEmail: String) {
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
                    dataStore.saveEmailToDataStore(email = email)
                    _uiState.value = ResetPasswordScreenUiState.Success
                } else if (result.isSuccess) {
                    _uiState.value = ResetPasswordScreenUiState.Error(
                       error = resourceProvider.getString(R.string.email_not_exists)
                    )
                } else {
                    _uiState.value = ResetPasswordScreenUiState.Error(
                        error = resourceProvider.getString(R.string.server_error)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _uiState.value =
                    ResetPasswordScreenUiState.Error(resourceProvider.getString(R.string.invalid_email))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

@Immutable
internal sealed interface ResetPasswordScreenUiState {
    data object Init : ResetPasswordScreenUiState
    data class Error(val error: String) : ResetPasswordScreenUiState
    data object Success : ResetPasswordScreenUiState
}

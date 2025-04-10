package screens.reset_password.otp

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

internal class OtpScreenViewModel(
    private val resourceProvider: ResourceProvider,
    private val datastore: AuthDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow<OtpScreenUiState>(OtpScreenUiState.Init)
    val uiState: StateFlow<OtpScreenUiState> = _uiState.asStateFlow()

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code.asStateFlow()

    infix fun updateCode(code: String) {
        _code.value = code
    }

    fun updateUiState() {
        _uiState.value = OtpScreenUiState.Initialized
    }

    infix fun sendVerificationCode(code: String) {
        viewModelScope.launch {
            val email = datastore.loadEmailFromDataStore()

            val result = network.reset_password.verifyCode(code = code, email = email)

            if (result.isSuccess && result.getOrNull() == "success") {
                _uiState.value = OtpScreenUiState.Success
            } else if (result.isSuccess) {
                _uiState.value = OtpScreenUiState.Error(
                    errorMessage = resourceProvider.getString(R.string.incorrect_code)
                )
            } else {
                _uiState.value = OtpScreenUiState.Error(
                    errorMessage = resourceProvider.getString(R.string.server_error)
                )
            }
        }
    }
}

@Immutable
internal sealed interface OtpScreenUiState {
    data object Init : OtpScreenUiState
    data object Initialized : OtpScreenUiState
    data object Success : OtpScreenUiState
    data class Error(val errorMessage: String) : OtpScreenUiState
}
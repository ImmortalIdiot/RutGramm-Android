package screens.signup

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.auth.R
import domain.AuthStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SignUpConfirmationViewModel(
    private val applicationContext: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<SignUpConfirmationUiState>(SignUpConfirmationUiState.Init)
    val uiState: StateFlow<SignUpConfirmationUiState> = _uiState.asStateFlow()

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code.asStateFlow()

    infix fun updateCode(newCode: String) {
        _code.value = newCode
    }

    infix fun verifyCode(code: String) {
        _uiState.value = SignUpConfirmationUiState.Loading

        var userId: String?

        viewModelScope.launch {
            userId = AuthStore.UserId loadUserId applicationContext

            if (userId != null) {
                val result = network.registration.verifyCode(code = code, userId = userId!!)

                _uiState.value = when {
                    result.isSuccess -> SignUpConfirmationUiState.Success

                    else -> {
                        SignUpConfirmationUiState.Error(
                            message = applicationContext.getString(R.string.smth_went_wrong_error)
                        )
                    }
                }
            } else {
                _uiState.value = SignUpConfirmationUiState.Error(
                    applicationContext.getString(R.string.smth_went_wrong_error)
                )
            }
        }
    }
}

@Immutable
internal sealed interface SignUpConfirmationUiState {
    data object Init : SignUpConfirmationUiState
    data object Loading : SignUpConfirmationUiState
    data object Success : SignUpConfirmationUiState
    data class Error(val message: String) : SignUpConfirmationUiState
}

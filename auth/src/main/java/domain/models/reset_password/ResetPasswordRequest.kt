package domain.models.reset_password

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResetPasswordRequest(
    val email: String,
    @SerialName("new_password") val newPassword: String
)

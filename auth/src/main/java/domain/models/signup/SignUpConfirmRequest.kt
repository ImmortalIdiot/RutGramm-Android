package domain.models.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpConfirmRequest(
    val code: String,
    @SerialName("user_id") val userId: String
)

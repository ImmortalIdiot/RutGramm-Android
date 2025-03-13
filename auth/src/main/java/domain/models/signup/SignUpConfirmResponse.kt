package domain.models.signup

import kotlinx.serialization.Serializable

@Serializable
data class SignUpConfirmResponse(
    val message: String
)

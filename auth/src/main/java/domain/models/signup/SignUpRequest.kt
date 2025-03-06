package domain.models.signup

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val login: String,
    val email: String,
    val password: String
)

package domain.models.signup

import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequest(
    val login: String,
    val email: String,
    val password: String
)

package domain.models.login

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

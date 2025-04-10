package domain.models.refresh

import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)

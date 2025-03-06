package domain

internal data class AuthData(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

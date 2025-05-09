package domain.models.reset_password

import kotlinx.serialization.Serializable

@Serializable
internal data class VerifyCodeRequest(
    val code: String,
    val email: String
)

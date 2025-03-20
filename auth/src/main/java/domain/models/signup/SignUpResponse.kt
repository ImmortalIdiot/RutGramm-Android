package domain.models.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpResponse(
    @SerialName("user_id") val userId: String,
    val message: String,
)

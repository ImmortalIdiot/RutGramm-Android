package network

import com.immortalidiot.auth.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal val client = HttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}

internal object Auth {
    const val LOGIN_URL = BuildConfig.AUTH_URL + "login"
    const val REGISTER_URL = BuildConfig.AUTH_URL + "register"
    const val CONFIRM_REGISTRATION_URL = BuildConfig.AUTH_URL + "verify-registration"
    const val SEND_EMAIL = BuildConfig.AUTH_URL + "password-reset"
    const val VERIFY_RESET_CODE = BuildConfig.AUTH_URL + "verify-reset-code"
    const val RESET_PASSWORD = BuildConfig.AUTH_URL + "reset-password"
    const val REFRESH = BuildConfig.AUTH_URL + "refresh"
}

package network.refresh

import domain.models.refresh.RefreshTokenResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import network.Auth
import network.client

internal suspend fun refreshToken(refreshToken: String): Result<RefreshTokenResponse> {
    return try {
        val response: RefreshTokenResponse = client.get(Auth.REFRESH) {
            headers {
                append(HttpHeaders.Authorization, refreshToken)
            }
        }.body()

        Result.success(response)
    } catch (responseException: ResponseException) {
        Result.failure(responseException)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

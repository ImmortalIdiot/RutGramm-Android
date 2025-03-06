package network.login

import domain.models.login.LoginRequest
import domain.models.login.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import network.Auth
import network.client

internal suspend fun authenticate(login: String, password: String): Result<LoginResponse> {
    return try {
        val loginRequest = LoginRequest(login = login, password = password)

        val response: LoginResponse = client.post(Auth.LOGIN_URL) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()

        Result.success(response)
    } catch (responseException: ResponseException) {
        Result.failure(responseException)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

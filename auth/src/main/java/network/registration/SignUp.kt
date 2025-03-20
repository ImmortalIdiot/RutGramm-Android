package network.registration

import domain.models.signup.SignUpConfirmRequest
import domain.models.signup.SignUpRequest
import domain.models.signup.SignUpResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import network.Auth
import network.client

internal suspend fun register(
    login: String,
    email: String,
    password: String
): Result<SignUpResponse> {
    return try {
        val signUpRequest = SignUpRequest(login, email, password)

        val response: SignUpResponse = client.post(Auth.REGISTER_URL) {
            contentType(ContentType.Application.Json)
            setBody(signUpRequest)
        }.body()

        Result.success(response)
    } catch (responseException: ResponseException) {
        Result.failure(responseException)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

internal suspend fun verifyCode(
    code: String,
    userId: String
): Result<String> {
    return try {
        val signUpConfirmRequest = SignUpConfirmRequest(code = code, userId = userId)

        val response: String = client.post(Auth.CONFIRM_REGISTRATION_URL) {
            contentType(ContentType.Application.Json)
            setBody(signUpConfirmRequest)
        }.body()

        Result.success(response)
    } catch (responseException: ResponseException) {
        Result.failure(responseException)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

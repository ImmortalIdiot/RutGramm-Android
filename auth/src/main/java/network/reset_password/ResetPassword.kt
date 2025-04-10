package network.reset_password

import domain.models.reset_password.ResetPasswordRequest
import domain.models.reset_password.VerifyCodeRequest
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import network.Auth
import network.client

internal suspend fun sendEmail(email: String): Result<String> {
    return try {
        val response: String = client.post(Auth.SEND_EMAIL) {
            contentType(ContentType.Application.Json)
            setBody(email)
        }.body()

        Result.success(response)
    } catch (responseException: ResponseException) {
        Result.failure(responseException)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

internal suspend fun verifyCode(code: String, email: String): Result<String> {
    return try {
        val verifyCodeRequest = VerifyCodeRequest(code = code, email = email)

        val response: String = client.post(Auth.VERIFY_RESET_CODE) {
            contentType(ContentType.Application.Json)
            setBody(verifyCodeRequest)
        }.body()

        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

internal suspend fun resetPassword(email: String, newPassword: String): Result<String> {
    return try {
        val resetPasswordRequest = ResetPasswordRequest(email = email, newPassword = newPassword)

        val response: String = client.post(Auth.RESET_PASSWORD) {
            contentType(ContentType.Application.Json)
            setBody(resetPasswordRequest)
        }.body()

        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
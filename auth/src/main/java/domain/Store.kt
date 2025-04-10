package domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

internal interface AuthDataStore {
    suspend fun saveUserId(userId: String)
    suspend fun loadUserId(): String?

    suspend fun saveAuthData(data: AuthData)
    suspend fun loadAuthData(): AuthData?

    suspend fun saveEmailToDataStore(email: String)
    suspend fun loadEmailFromDataStore(): String
}

internal class AuthDataStoreImpl(private val context: Context) : AuthDataStore {
    private val userIdKey = stringPreferencesKey(name = "user_id")
    private val accessTokenKey = stringPreferencesKey(name = "access_token")
    private val refreshTokenKey = stringPreferencesKey(name = "refresh_token")

    private val email = stringPreferencesKey(name = "email")

    private val Context.authDataStore by preferencesDataStore(name = "AuthData")

    override suspend fun saveUserId(userId: String) {
        context.authDataStore.edit {
            it[userIdKey] = userId
        }
    }

    override suspend fun loadUserId(): String? {
        val preferences = context.authDataStore.data.first()
        return preferences[userIdKey]
    }

    override suspend fun saveAuthData(data: AuthData) {
        context.authDataStore.edit {
            it[userIdKey] = data.userId
            it[accessTokenKey] = data.accessToken
            it[refreshTokenKey] = data.refreshToken
        }
    }

    override suspend fun loadAuthData(): AuthData? {
        val preferences = context.authDataStore.data.first()

        val userId = preferences[userIdKey]
        val accessToken = preferences[accessTokenKey]
        val refreshToken = preferences[refreshTokenKey]

        return if (userId != null && accessToken != null && refreshToken != null) {
            AuthData(userId, accessToken, refreshToken)
        } else {
            null
        }
    }

    override suspend fun saveEmailToDataStore(email: String) {
        context.authDataStore.edit {
            it[this.email] = email
        }
    }

    override suspend fun loadEmailFromDataStore(): String {
        val preferences = context.authDataStore.data.first()
        return preferences[email] ?: ""
    }
}

package domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

internal object AuthStore {
    private val KEY_USER_ID = stringPreferencesKey(name = "user_id")
    private val KEY_ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")

    private val EMAIL = stringPreferencesKey(name = "email")

    private val Context.authDataStore by preferencesDataStore(name = "AuthData")

    object UserId {
        suspend fun saveUserId(context: Context, userId: String) {
            context.authDataStore.edit {
                it[KEY_USER_ID] = userId
            }
        }

        suspend infix fun loadUserId(context: Context): String? {
            val preferences = context.authDataStore.data.first()
            return preferences[KEY_USER_ID]
        }
    }

    object Data {
        suspend fun saveAuthData(context: Context, data: AuthData) {
            context.authDataStore.edit {
                it[KEY_USER_ID] = data.userId
                it[KEY_ACCESS_TOKEN] = data.accessToken
                it[KEY_REFRESH_TOKEN] = data.refreshToken
            }
        }

        suspend infix fun loadAuthData(context: Context): AuthData? {
            val preferences = context.authDataStore.data.first()

            val userId = preferences[KEY_USER_ID]
            val accessToken = preferences[KEY_ACCESS_TOKEN]
            val refreshToken = preferences[KEY_REFRESH_TOKEN]

            return if (userId != null && accessToken != null && refreshToken != null) {
                AuthData(userId, accessToken, refreshToken)
            } else {
                null
            }
        }
    }

    object Email {
        suspend fun saveEmailToDataStore(context: Context, email: String) {
            context.authDataStore.edit {
                it[EMAIL] = email
            }
        }

        suspend infix fun loadEmailFromDataStore(context: Context): String {
            val preferences = context.authDataStore.data.first()
            return preferences[EMAIL] ?: ""
        }
    }
}

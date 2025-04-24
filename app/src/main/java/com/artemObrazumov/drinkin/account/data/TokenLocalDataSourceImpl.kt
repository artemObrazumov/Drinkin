package com.artemObrazumov.drinkin.account.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.artemObrazumov.drinkin.account.domain.data_source.TokenLocalDataSource
import com.artemObrazumov.drinkin.account.domain.models.Tokens
import com.artemObrazumov.drinkin.account.presentation.utils.TokenLocalStorageConstants.ACCESS_TOKEN
import com.artemObrazumov.drinkin.account.presentation.utils.TokenLocalStorageConstants.REFRESH_TOKEN
import com.artemObrazumov.drinkin.account.presentation.utils.tokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenLocalDataSourceImpl(
    private val context: Context
): TokenLocalDataSource {

    override fun getTokensFlow(): Flow<Tokens?> {
        return context.tokenDataStore.data.map { preferences ->
            val accessToken = preferences[ACCESS_TOKEN]
            val refreshToken = preferences[REFRESH_TOKEN]
            return@map if (accessToken == null || refreshToken == null) {
                null
            } else {
                Tokens(accessToken, refreshToken)
            }
        }
    }

    override suspend fun saveTokens(tokens: Tokens) {
        context.tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokens.accessToken
            preferences[REFRESH_TOKEN] = tokens.refreshToken
        }
    }

    override suspend fun removeTokens() {
        context.tokenDataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }
}
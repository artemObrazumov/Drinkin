package com.artemObrazumov.drinkin.account.domain.data_source

import com.artemObrazumov.drinkin.account.domain.models.Tokens
import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {

    fun getTokensFlow(): Flow<Tokens?>
    suspend fun saveTokens(tokens: Tokens)
    suspend fun removeTokens()
}
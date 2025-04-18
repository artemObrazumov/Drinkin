package com.artemObrazumov.drinkin.account.domain.data_source

import com.artemObrazumov.drinkin.account.domain.models.Tokens
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {

    fun getTokenFlow(): Flow<Tokens?>
    suspend fun saveTokens(tokens: Tokens)
    suspend fun login(login: String, password: String): Result<Tokens, Error>
    suspend fun register(login: String, password: String): Result<Tokens, Error>
}
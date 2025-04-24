package com.artemObrazumov.drinkin.account.data

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.data_source.TokenLocalDataSource
import com.artemObrazumov.drinkin.account.domain.models.Tokens
import com.artemObrazumov.drinkin.core.domain.util.AuthorizationError
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

class LoginMockDataSource(
    private val tokenLocalDataSource: TokenLocalDataSource
): LoginDataSource {

    override fun getTokenFlow(): Flow<Tokens?> {
        return tokenLocalDataSource.getTokensFlow()
    }

    override suspend fun saveTokens(tokens: Tokens) {
        tokenLocalDataSource.saveTokens(tokens)
    }

    override suspend fun login(login: String, password: String): Result<Tokens, Error> {
        if (password == "123456") {
            return Result.Success(Tokens("1111", "2222"))
        }
        return Result.Error(AuthorizationError.CREDENTIALS_INCORRECT)
    }

    override suspend fun register(login: String, password: String): Result<Tokens, Error> {
        return Result.Success(Tokens("1111", "2222"))
    }

    override suspend fun logout() {
        tokenLocalDataSource.removeTokens()
    }
}
package com.artemObrazumov.drinkin.account.data

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.models.Tokens
import com.artemObrazumov.drinkin.core.domain.util.AuthorizationError
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

class LoginMockDataSource(
    private val tokenLocalMockDataSource: TokenLocalMockDataSource
): LoginDataSource {

    override fun getTokenFlow(): Flow<Tokens?> {
        return tokenLocalMockDataSource.getTokensFlow()
    }

    override suspend fun saveTokens(tokens: Tokens) {
        tokenLocalMockDataSource.saveTokens(tokens)
    }

    override suspend fun login(login: String, password: String): Result<Tokens, Error> {
        if (password == "123456") {
            return Result.Success(Tokens("1111", "2222"))
        }
        return Result.Error(AuthorizationError.CREDENTIALS_INCORRECT)
    }

    override suspend fun register(login: String, password: String): Result<Tokens, Error> {
        TODO("Not yet implemented")
    }
}
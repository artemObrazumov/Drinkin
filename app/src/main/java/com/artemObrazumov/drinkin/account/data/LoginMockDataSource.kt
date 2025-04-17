package com.artemObrazumov.drinkin.account.data

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.models.Token
import com.artemObrazumov.drinkin.core.domain.util.AuthorizationError
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

class LoginMockDataSource: LoginDataSource {

    override fun getTokenFlow(): Flow<Token?> {
        TODO("Not yet implemented")
    }

    override suspend fun login(login: String, password: String): Result<Int, Error> {
        if (password == "123456") {
            return Result.Success(200)
        }
        return Result.Error(AuthorizationError.CREDENTIALS_INCORRECT)
    }

    override suspend fun register(login: String, password: String): Result<Int, Error> {
        TODO("Not yet implemented")
    }
}
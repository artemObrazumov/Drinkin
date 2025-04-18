package com.artemObrazumov.drinkin.account.data

import com.artemObrazumov.drinkin.account.domain.data_source.UserDataSource
import com.artemObrazumov.drinkin.account.domain.models.User
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class UserMockDataSource: UserDataSource {

    private val userFlow = MutableStateFlow<User?>(null)

    override fun getUserFlow(): Flow<User?> {
        return userFlow
    }

    override suspend fun authorizeUser(): Result<Int, Error> {
        delay(2000)
        userFlow.emit(User("test user"))
        return Result.Success(200)
    }
}
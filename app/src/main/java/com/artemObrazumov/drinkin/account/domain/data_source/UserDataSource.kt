package com.artemObrazumov.drinkin.account.domain.data_source

import com.artemObrazumov.drinkin.account.domain.models.User
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    fun getUserFlow(): Flow<User?>
    suspend fun authorizeUser(): Result<Int, Error>
    suspend fun logoutUser()
}
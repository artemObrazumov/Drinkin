package com.artemObrazumov.drinkin.account.domain.data_source

import com.artemObrazumov.drinkin.account.domain.models.Token
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {

    fun getTokenFlow(): Flow<Token?>
    suspend fun login(login: String, password: String): Result<Int, NetworkError>
    suspend fun register(login: String, password: String): Result<Int, NetworkError>
}
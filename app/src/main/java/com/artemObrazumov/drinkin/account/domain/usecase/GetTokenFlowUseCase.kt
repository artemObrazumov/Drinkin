package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.models.Tokens
import kotlinx.coroutines.flow.Flow

class GetTokenFlowUseCase(
    private val loginDataSource: LoginDataSource
) {

    operator fun invoke(): Flow<Tokens?> {
        return loginDataSource.getTokenFlow()
    }
}
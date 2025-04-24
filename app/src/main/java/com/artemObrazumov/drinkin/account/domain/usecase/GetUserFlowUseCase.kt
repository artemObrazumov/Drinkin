package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.UserDataSource
import com.artemObrazumov.drinkin.account.domain.models.User
import kotlinx.coroutines.flow.Flow

class GetUserFlowUseCase(
    private val userDataSource: UserDataSource
) {

    operator fun invoke(): Flow<User?> {
        return userDataSource.getUserFlow()
    }
}
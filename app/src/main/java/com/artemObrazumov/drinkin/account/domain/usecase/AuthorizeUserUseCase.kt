package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.UserDataSource
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class AuthorizeUserUseCase(
    private val userDataSource: UserDataSource
) {

    suspend operator fun invoke(): AuthorizeUserUseCaseResult {
        return when(val result = userDataSource.authorizeUser()) {
            is Result.Error -> {
                AuthorizeUserUseCaseResult.Failure(
                    error = result.error
                )
            }
            is Result.Success -> {
                userDataSource.authorizeUser()
                AuthorizeUserUseCaseResult.Success
            }
        }
    }
}

sealed class AuthorizeUserUseCaseResult {

    data class Failure(
        val error: Error
    ): AuthorizeUserUseCaseResult()

    data object Success: AuthorizeUserUseCaseResult()
}
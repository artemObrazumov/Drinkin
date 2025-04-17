package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class LoginUseCase(
    private val loginDataSource: LoginDataSource,
    private val saveTokensUseCase: SaveTokensUseCase
) {

    suspend operator fun invoke(
        login: String,
        password: String
    ): LoginUseCaseResult {
        return when(val result = loginDataSource.login(login, password)) {
            is Result.Error -> {
                LoginUseCaseResult.Failure(result.error)
            }
            is Result.Success -> {
                when (val saveTokensResult = saveTokensUseCase()) {
                    is SaveTokensUseCaseResult.Failure -> {
                        LoginUseCaseResult.Failure(saveTokensResult.error)
                    }
                    SaveTokensUseCaseResult.Success -> {
                        LoginUseCaseResult.Success
                    }
                }
            }
        }
    }
}

sealed class LoginUseCaseResult {

    data class Failure(
        val error: Error
    ): LoginUseCaseResult()

    data object Success: LoginUseCaseResult()
}
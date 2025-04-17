package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class RegisterUseCase(
    private val loginDataSource: LoginDataSource,
    private val saveTokensUseCase: SaveTokensUseCase
) {

    suspend operator fun invoke(
        login: String,
        password: String
    ): RegisterUseCaseResult {
        return when(val result = loginDataSource.register(login, password)) {
            is Result.Error -> {
                RegisterUseCaseResult.Failure(result.error)
            }
            is Result.Success -> {
                when (val saveTokensResult = saveTokensUseCase()) {
                    is SaveTokensUseCaseResult.Failure -> {
                        RegisterUseCaseResult.Failure(saveTokensResult.error)
                    }
                    SaveTokensUseCaseResult.Success -> {
                        RegisterUseCaseResult.Success
                    }
                }
            }
        }
    }
}

sealed class RegisterUseCaseResult {

    data class Failure(
        val error: Error
    ): RegisterUseCaseResult()

    data object Success: RegisterUseCaseResult()
}
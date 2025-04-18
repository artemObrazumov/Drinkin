package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.models.Tokens
import com.artemObrazumov.drinkin.core.domain.util.Error

class SaveTokensUseCase(
    private val loginDataSource: LoginDataSource
) {

    suspend operator fun invoke(tokens: Tokens): SaveTokensUseCaseResult {
        loginDataSource.saveTokens(tokens)
        return SaveTokensUseCaseResult.Success
    }
}

sealed class SaveTokensUseCaseResult {

    data class Failure(
        val error: Error
    ): SaveTokensUseCaseResult()

    data object Success: SaveTokensUseCaseResult()
}
package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error

class SaveTokensUseCase {

    operator fun invoke(): SaveTokensUseCaseResult {
        TODO()
    }
}

sealed class SaveTokensUseCaseResult {

    data class Failure(
        val error: Error
    ): SaveTokensUseCaseResult()

    data object Success: SaveTokensUseCaseResult()
}
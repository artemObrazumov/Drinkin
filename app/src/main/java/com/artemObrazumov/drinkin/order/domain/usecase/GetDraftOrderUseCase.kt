package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.DraftOrder
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class GetDraftOrderUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(): GetOrderUseCaseResult {
        return when(val result = orderDataSource.getDraftOrderFromServer()) {
            is Result.Success -> {
                GetOrderUseCaseResult.Success(result.data)
            }
            is Result.Error -> {
                GetOrderUseCaseResult.Failure(result.error)
            }
        }
    }
}

sealed class GetOrderUseCaseResult {

    data class Failure(
        val error: Error
    ): GetOrderUseCaseResult()

    data class Success(
        val draftOrder: DraftOrder
    ): GetOrderUseCaseResult()
}
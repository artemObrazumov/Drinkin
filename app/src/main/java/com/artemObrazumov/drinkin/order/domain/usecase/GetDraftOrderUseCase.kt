package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.DraftOrder
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class GetDraftOrderUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(): GetDraftOrderResult {
        return when(val result = orderDataSource.getDraftOrderFromServer()) {
            is Result.Success -> {
                GetDraftOrderResult.Success(result.data)
            }
            is Result.Error -> {
                GetDraftOrderResult.Failure(result.error)
            }
        }
    }
}

sealed class GetDraftOrderResult {

    data class Failure(
        val error: Error
    ): GetDraftOrderResult()

    data class Success(
        val draftOrder: DraftOrder
    ): GetDraftOrderResult()
}
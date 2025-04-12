package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource

class UpdateOrderItemsListUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(): UpdateOrderItemsListResult {
        return when(val result = orderDataSource.updateOrderItems()) {
            is Result.Success -> {
                UpdateOrderItemsListResult.Success
            }

            is Result.Error -> {
                UpdateOrderItemsListResult.Failure(error = result.error)
            }
        }
    }
}

sealed class UpdateOrderItemsListResult {
    data object Success: UpdateOrderItemsListResult()
    data class Failure(
        val error: Error
    ): UpdateOrderItemsListResult()
}
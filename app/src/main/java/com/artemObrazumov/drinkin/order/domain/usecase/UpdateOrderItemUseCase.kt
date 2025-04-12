package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource

class UpdateOrderItemUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(orderId: Int): UpdateOrderItemsListResult {
        return when(val result = orderDataSource.updateOrderItem(orderId)) {
            is Result.Success -> {
                UpdateOrderItemsListResult.Success
            }

            is Result.Error -> {
                UpdateOrderItemsListResult.Failure(error = result.error)
            }
        }
    }
}

sealed class UpdateOrderItemResult {
    data object Success: UpdateOrderItemResult()
    data class Failure(
        val error: Error
    ): UpdateOrderItemResult()
}
package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource

class SaveOrderUseCase(
    private val orderDataSource: OrderDataSource,
    private val getOrderUseCase: GetOrderUseCase
) {

    suspend operator fun invoke(orderId: Int): SaveOrderResult {
        return when(val result = getOrderUseCase.invoke(orderId)) {
            is GetOrderResult.Success -> {
                orderDataSource.saveOrderToOrderItems(result.order)
                SaveOrderResult.Success
            }

            is GetOrderResult.Failure -> {
                SaveOrderResult.Failure(error = result.error)
            }
        }
    }
}

sealed class SaveOrderResult {

    data class Failure(
        val error: NetworkError
    ): SaveOrderResult()

    data object Success: SaveOrderResult()
}

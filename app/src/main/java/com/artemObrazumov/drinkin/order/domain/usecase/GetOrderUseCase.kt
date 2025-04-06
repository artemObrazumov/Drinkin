package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.Order

class GetOrderUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(orderId: Int): GetOrderResult {
        return when(val result = orderDataSource.getOrder(orderId)) {
            is Result.Success -> {
                GetOrderResult.Success(result.data)
            }

            is Result.Error -> {
                GetOrderResult.Failure(error = result.error)
            }
        }
    }
}

sealed class GetOrderResult {

    data class Failure(
        val error: NetworkError
    ): GetOrderResult()

    data class Success(
        val order: Order
    ): GetOrderResult()
}

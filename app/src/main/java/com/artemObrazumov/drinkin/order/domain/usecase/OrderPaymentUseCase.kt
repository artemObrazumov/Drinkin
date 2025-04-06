package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.usecase.ClearCartUseCase
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource

class OrderPaymentUseCase(
    private val orderDataSource: OrderDataSource,
    private val clearCartUseCase: ClearCartUseCase,
    private val saveOrderUseCase: SaveOrderUseCase
) {

    suspend fun invoke(orderId: Int): OrderPaymentResult {
        return when (val result = orderDataSource.payForOrder(orderId)) {
            is Result.Success -> {
                clearCartUseCase.invoke()
                saveOrderUseCase.invoke(orderId)
                OrderPaymentResult.Success
            }
            is Result.Error -> {
                OrderPaymentResult.Failure(result.error)
            }
        }
    }
}

sealed class OrderPaymentResult {

    data class Failure(
        val error: NetworkError
    ): OrderPaymentResult()

    data object Success: OrderPaymentResult()
}

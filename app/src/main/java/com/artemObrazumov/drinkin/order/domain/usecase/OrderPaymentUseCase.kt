package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.usecase.ClearCartUseCase
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.delay

class OrderPaymentUseCase(
    private val orderDataSource: OrderDataSource,
    private val clearCartUseCase: ClearCartUseCase
) {

    suspend fun invoke(orderId: Int): OrderPaymentResult {
        delay(2500)
        return when (val result = orderDataSource.payForOrder(orderId)) {
            is Result.Success -> {
                clearCartUseCase.invoke()
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

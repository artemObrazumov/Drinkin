package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.cart.domain.models.Order
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

class GetOrderUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke(): GetOrderUseCaseResult {
        return when(val result = orderDataSource.getOrderFromServer()) {
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
        val order: Order
    ): GetOrderUseCaseResult()
}
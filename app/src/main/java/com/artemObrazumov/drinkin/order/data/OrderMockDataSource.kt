package com.artemObrazumov.drinkin.order.data

import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.toProductInOrder
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.first

class OrderMockDataSource(
    private val getProductsInCartFlowUseCase: GetProductsInCartFlowUseCase
) : OrderDataSource {

    override suspend fun getOrderFromServer(): Result<Order, Error> {
        // Making order from products in cart
        return Result.Success(
            Order(
                id = 1,
                products = getProductsInCartFlowUseCase.invoke().first().map {
                    it.toProductInOrder()
                },
                address = "test address"
            )
        )
    }

    override suspend fun payForOrder(orderId: Int): Result<Int, NetworkError> {
        return Result.Success(200)
    }
}
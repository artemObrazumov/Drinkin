package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import kotlinx.coroutines.flow.Flow

class GetOrderItemsFlowUseCase(
    private val orderDataSource: OrderDataSource
) {

    operator fun invoke(): Flow<List<OrderItem>> {
        return orderDataSource.getOrderItemsFlow()
    }
}
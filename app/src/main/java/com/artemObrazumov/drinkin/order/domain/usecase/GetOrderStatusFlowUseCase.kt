package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.order.domain.data_source.OrderTrackerDataSource
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import kotlinx.coroutines.flow.Flow

class GetOrderStatusFlowUseCase(
    private val orderTrackerDataSource: OrderTrackerDataSource
) {

    operator fun invoke(orderId: Int): Flow<OrderStatus> {
        return orderTrackerDataSource.getOrderStatusFlow(orderId)
    }
}
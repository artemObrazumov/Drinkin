package com.artemObrazumov.drinkin.order.data

import com.artemObrazumov.drinkin.order.domain.data_source.OrderTrackerDataSource
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderTrackerMockDataSource: OrderTrackerDataSource {

    override fun getOrderStatusFlow(orderId: Int): Flow<OrderStatus> = flow {
        delay(7000)
        emit(OrderStatus.READY)
    }
}
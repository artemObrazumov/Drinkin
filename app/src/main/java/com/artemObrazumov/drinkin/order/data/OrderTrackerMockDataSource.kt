package com.artemObrazumov.drinkin.order.data

import com.artemObrazumov.drinkin.order.domain.data_source.OrderTrackerDataSource
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class OrderTrackerMockDataSource: OrderTrackerDataSource {

    override fun getOrderStatusFlow(orderId: Int): Flow<OrderStatus> = flow {
        delay(Random.nextInt(3, 5) * 1000L)
        emit(OrderStatus.READY)
    }
}
package com.artemObrazumov.drinkin.order.domain.data_source

import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderTrackerDataSource {

    fun getOrderStatusFlow(orderId: Int): Flow<OrderStatus>
}
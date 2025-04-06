package com.artemObrazumov.drinkin.order.domain.data_source

import com.artemObrazumov.drinkin.order.domain.models.DraftOrder
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import kotlinx.coroutines.flow.SharedFlow

interface OrderDataSource {

    /*
    * This method gets active order from the server in case the local one became invalid
    */
    suspend fun getDraftOrderFromServer(): Result<DraftOrder, Error>
    suspend fun payForOrder(orderId: Int): Result<Int, NetworkError>
    suspend fun getOrder(orderId: Int): Result<Order, NetworkError>
    suspend fun getOrderItem(orderId: Int): Result<OrderItem, NetworkError>
    suspend fun saveOrderToOrderItems(order: Order)
    suspend fun updateOrderItems(): Result<Int, Error>
    fun getOrderItemsFlow(): SharedFlow<List<OrderItem>>
}
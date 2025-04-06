package com.artemObrazumov.drinkin.order.presentation.orders

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.order.presentation.models.OrderItemUi

sealed class OrdersScreenState {

    data object Loading: OrdersScreenState()
    data class Content(
        val orders: List<OrderItemUi>
    ): OrdersScreenState()
    data class Failure(
        val error: Error
    ): OrdersScreenState()
}
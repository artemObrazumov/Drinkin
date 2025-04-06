package com.artemObrazumov.drinkin.order.data

import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.order.domain.models.DraftOrder
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import com.artemObrazumov.drinkin.order.domain.models.toOrderItem
import com.artemObrazumov.drinkin.order.domain.models.toProductInOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class OrderMockDataSource(
    private val getProductsInCartFlowUseCase: GetProductsInCartFlowUseCase
) : OrderDataSource {

    private val orderItemsScope = CoroutineScope(SupervisorJob())

    private val orderItems: MutableList<OrderItem> = mutableListOf()
    private val _orderItems = MutableSharedFlow<List<OrderItem>>(
        replay = 1,
        extraBufferCapacity = 1
    ).also { flow ->
        orderItemsScope.launch {
            flow.emit(orderItems)
        }
    }
    private val orderItemsFlow = _orderItems.asSharedFlow()

    private var draftOrder: DraftOrder? = null
    private val orders = mutableListOf<Order>()

    override suspend fun getDraftOrderFromServer(): Result<DraftOrder, Error> {
        // Making order from products in cart
        draftOrder = DraftOrder(
            id = 1,
            products = getProductsInCartFlowUseCase.invoke().first().map {
                it.toProductInOrder()
            },
            address = "test address"
        )
        draftOrder?.let {
            return Result.Success(it)
        }
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    override suspend fun payForOrder(orderId: Int): Result<Int, NetworkError> {
        val latestOrderId = orders.lastOrNull()?.id ?: 0
        draftOrder?.let {
            orders.add(Order(
                id = latestOrderId + 1,
                products = it.products,
                address = it.address,
                totalPrice = it.totalPrice,
                status = OrderStatus.IN_PROCESS,
                time = LocalDateTime.now()
            ))
        }
        draftOrder = null
        return Result.Success(200)
    }

    override suspend fun getOrder(orderId: Int): Result<Order, NetworkError> {
        return Result.Success(
            orders.first { it.id == orderId }
        )
    }

    override suspend fun getOrderItem(orderId: Int): Result<OrderItem, NetworkError> {
        return Result.Success(
            orders.first { it.id == orderId }
                .toOrderItem()
        )
    }

    override suspend fun saveOrderToOrderItems(order: Order) {
        orderItems.add(order.toOrderItem())
        _orderItems.emit(orderItems)
    }

    override suspend fun updateOrderItems(): Result<Int, Error> {
        // Updating order items list with server data
        return Result.Success(200)
    }

    override fun getOrderItemsFlow(): SharedFlow<List<OrderItem>> {
        return orderItemsFlow
    }
}
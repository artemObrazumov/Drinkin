package com.artemObrazumov.drinkin.order.domain.models

import java.time.LocalDateTime

data class OrderItem(
    val id: Int,
    val number: Int,
    val address: String,
    val status: OrderStatus,
    val time: LocalDateTime,
    val price: Float
)

fun Order.toOrderItem(): OrderItem {
    return OrderItem(
        id = id,
        number = number,
        address = address,
        status = status,
        time = time,
        price = totalPrice
    )
}

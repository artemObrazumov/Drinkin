package com.artemObrazumov.drinkin.order.domain.models

import java.time.LocalDateTime

data class OrderItem(
    val id: Int,
    val address: String,
    val status: OrderStatus,
    val time: LocalDateTime
)

fun Order.toOrderItem(): OrderItem {
    return OrderItem(
        id = id,
        address = address,
        status = status,
        time = time
    )
}

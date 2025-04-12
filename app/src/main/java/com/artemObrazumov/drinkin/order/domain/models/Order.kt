package com.artemObrazumov.drinkin.order.domain.models

import java.time.LocalDateTime

data class Order(
    val id: Int,
    val number: Int,
    val products: List<ProductInOrder>,
    val address: String,
    val totalPrice: Float,
    val status: OrderStatus,
    val time: LocalDateTime
)

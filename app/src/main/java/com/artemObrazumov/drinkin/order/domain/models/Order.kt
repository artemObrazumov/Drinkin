package com.artemObrazumov.drinkin.order.domain.models

data class Order(
    val id: Int,
    val products: List<ProductInOrder>,
    val address: String
) {
    val totalPrice: Float = products.sumOf { it.price.toDouble() }.toFloat()
}

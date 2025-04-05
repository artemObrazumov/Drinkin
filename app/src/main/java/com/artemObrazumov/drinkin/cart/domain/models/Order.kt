package com.artemObrazumov.drinkin.cart.domain.models

data class Order(
    val id: Int,
    val products: List<ProductInOrder>,
    val address: String
) {
    val totalPrice: Float = products.sumOf { it.price.toDouble() }.toFloat()
}

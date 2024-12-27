package com.artemObrazumov.drinkin.cart.domain.models

data class Order(
    val id: Int? = null,
    val products: List<ProductInOrder>
) {
    val totalPrice: Float = products.sumOf { it.price.toDouble() }.toFloat()
}

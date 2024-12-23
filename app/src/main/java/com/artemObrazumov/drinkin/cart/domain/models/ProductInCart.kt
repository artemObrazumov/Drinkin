package com.artemObrazumov.drinkin.cart.domain.models

data class ProductInCart(
    val id: Int,
    val productId: Int,
    val name: String,
    val price: Float,
    val quantity: Int,
    val imageRes: Int,
    val parameters: Map<String, String>
)

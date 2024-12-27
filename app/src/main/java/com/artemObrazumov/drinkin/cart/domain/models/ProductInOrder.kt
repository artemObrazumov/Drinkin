package com.artemObrazumov.drinkin.cart.domain.models

data class ProductInOrder(
    val id: Int,
    val productId: Int,
    val name: String,
    val price: Float,
    val quantity: Int,
    val imageRes: Int,
    val parameters: Map<String, String>
)

fun ProductInCart.toProductInOrder(): ProductInOrder {
    return ProductInOrder(
        id = id,
        productId = productId,
        name = name,
        price = price,
        quantity = quantity,
        imageRes = imageRes,
        parameters = parameters
    )
}

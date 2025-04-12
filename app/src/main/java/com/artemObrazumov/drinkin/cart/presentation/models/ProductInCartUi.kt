package com.artemObrazumov.drinkin.cart.presentation.models

import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT

data class ProductInCartUi(
    val id: Int,
    val productId: Int,
    val name: String,
    val price: FormattedValue<Float>,
    val quantity: Int,
    val imageRes: Int,
    val parameters: Map<String, String>
)

fun ProductInCart.toProductInCartUi(): ProductInCartUi {
    return ProductInCartUi(
        id = id,
        productId = productId,
        name = name,
        price = price.asFormattedPrice(PRICE_UNIT),
        quantity = quantity,
        imageRes = imageRes,
        parameters = parameters
    )
}

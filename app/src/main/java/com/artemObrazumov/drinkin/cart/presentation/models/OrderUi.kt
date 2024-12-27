package com.artemObrazumov.drinkin.cart.presentation.models

import com.artemObrazumov.drinkin.cart.domain.models.Order
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT

data class OrderUi(
    val id: Int? = null,
    val products: List<ProductInOrderUi>,
    val totalPrice: FormattedValue<Float>
)

fun Order.toOrderUi(): OrderUi {
    return OrderUi(
        id = id,
        products = products.map {
            it.toProductInOrderUi()
        },
        totalPrice = totalPrice.asFormattedPrice(PRICE_UNIT)
    )
}

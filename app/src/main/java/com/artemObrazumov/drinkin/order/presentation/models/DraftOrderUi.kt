package com.artemObrazumov.drinkin.order.presentation.models

import com.artemObrazumov.drinkin.order.domain.models.DraftOrder
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT

data class DraftOrderUi(
    val id: Int,
    val products: List<ProductInOrderUi>,
    val address: String,
    val totalPrice: FormattedValue<Float>
)

fun DraftOrder.toDraftOrderUi(): DraftOrderUi {
    return DraftOrderUi(
        id = id,
        products = products.map {
            it.toProductInOrderUi()
        },
        address = address,
        totalPrice = totalPrice.asFormattedPrice(PRICE_UNIT)
    )
}

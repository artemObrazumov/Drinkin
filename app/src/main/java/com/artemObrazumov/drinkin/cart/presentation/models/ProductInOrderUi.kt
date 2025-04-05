package com.artemObrazumov.drinkin.cart.presentation.models

import com.artemObrazumov.drinkin.cart.domain.models.ProductInOrder
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.presentation.asFormattedQuantity
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT

data class ProductInOrderUi(
    val id: Int,
    val productId: Int,
    val name: String,
    val price: FormattedValue<Float>,
    val quantity: FormattedValue<Int>,
    val imageRes: Int,
    val parameters: Map<String, String>,
    val parametersString: String
)

fun ProductInOrder.toProductInOrderUi(): ProductInOrderUi {
    return ProductInOrderUi(
        id = id,
        productId = productId,
        name = name,
        price = price.asFormattedPrice(PRICE_UNIT),
        quantity = quantity.asFormattedQuantity(),
        imageRes = imageRes,
        parameters = parameters,
        parametersString = parameters.map { "${it.key}: ${it.value}" }
            .joinToString("\n")
    )
}

package com.artemObrazumov.drinkin.product.presentation.models

import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.product.domain.models.Product

data class ProductUi(
    val id: Int,
    val name: String,
    val price: FormattedValue<Float>,
    val salePrice: FormattedValue<Float>?,
    val category: String?,
    val imageRes: Int
)

fun Product.toProductUi(unit: String): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        price = price.asFormattedPrice(unit),
        salePrice = salePrice?.asFormattedPrice(unit),
        category = category,
        imageRes = imageRes
    )
}
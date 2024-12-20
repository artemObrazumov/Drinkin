package com.artemObrazumov.drinkin.dashboard.presentation.models

import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

data class ProductDetailsUi(
    val id: Int,
    val name: String,
    val basePrice: FormattedValue<Float>,
    val salePrice: FormattedValue<Float>?,
    val category: String?,
    val imageRes: Int,
    val description: String,
    val customizableParams: List<CustomizableParameterUi>
)

fun ProductDetails.toProductDetailsUi(unit: String): ProductDetailsUi {
    return ProductDetailsUi(
        id = id,
        name = name,
        basePrice = basePrice.asFormattedPrice(unit),
        salePrice = salePrice?.asFormattedPrice(unit),
        category = category,
        imageRes = imageRes,
        description = description,
        customizableParams = customizableParams.map {
            it.toCustomizableParameterUi()
        }
    )
}
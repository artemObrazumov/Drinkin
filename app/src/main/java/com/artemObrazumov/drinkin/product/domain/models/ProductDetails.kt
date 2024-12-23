package com.artemObrazumov.drinkin.product.domain.models

data class ProductDetails(
    val id: Int,
    val name: String,
    val basePrice: Float,
    val salePrice: Float?,
    val category: String?,
    val imageRes: Int,
    val description: String,
    val customizableParams: List<CustomizableParameter>
)

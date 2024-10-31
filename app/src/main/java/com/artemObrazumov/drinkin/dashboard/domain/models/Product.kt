package com.artemObrazumov.drinkin.dashboard.domain.models

data class Product(
    val id: Int,
    val name: String,
    val price: Float,
    val salePrice: Float?,
    val category: String?,
    val imageRes: Int
)

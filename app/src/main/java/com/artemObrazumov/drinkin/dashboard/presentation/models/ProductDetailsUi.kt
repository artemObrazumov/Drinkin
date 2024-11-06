package com.artemObrazumov.drinkin.dashboard.presentation.models

import com.artemObrazumov.drinkin.core.presentation.FormattedValue

data class ProductDetailsUi(
    val id: Int,
    val name: String,
    val price: FormattedValue<Float>,
    val salePrice: FormattedValue<Float>?,
    val category: String?,
    val imageRes: Int
)
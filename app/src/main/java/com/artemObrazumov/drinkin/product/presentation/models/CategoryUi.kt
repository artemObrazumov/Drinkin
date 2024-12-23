package com.artemObrazumov.drinkin.product.presentation.models

import com.artemObrazumov.drinkin.product.domain.models.Category

data class CategoryUi(
    val name: String,
    val title: String,
    val imageRes: Int
)

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(
        name = name,
        title = title,
        imageRes = imageRes
    )
}
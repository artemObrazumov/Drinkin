package com.artemObrazumov.drinkin.dashboard.presentation.models

import com.artemObrazumov.drinkin.dashboard.domain.models.Category

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
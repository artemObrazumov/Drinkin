package com.artemObrazumov.drinkin.dashboard.domain.models

data class CustomizableParameter(
    val name: String,
    val options: List<CustomizableParameterOption>
)

data class CustomizableParameterOption(
    val name: String,
    val detail: String,
    val priceDifference: Float,
    val imageRes: Int
)
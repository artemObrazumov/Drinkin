package com.artemObrazumov.drinkin.product.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object DashBoard

@Serializable
data class Details(
    val productId: Int
)
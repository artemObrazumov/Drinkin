package com.artemObrazumov.drinkin.order.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object NewOrder

@Serializable
data object OrderSuccess

@Serializable
data object Orders

@Serializable
data class OrderDetails(
    val id: Int
)

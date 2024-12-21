package com.artemObrazumov.drinkin.dashboard.presentation.cart

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart

@Immutable
sealed class CartScreenState {
    data object Loading: CartScreenState()
    data class Content(
        val products: List<ProductInCart> = emptyList()
    ): CartScreenState()
}
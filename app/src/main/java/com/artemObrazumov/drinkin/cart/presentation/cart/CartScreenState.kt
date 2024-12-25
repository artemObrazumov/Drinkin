package com.artemObrazumov.drinkin.cart.presentation.cart

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart

@Immutable
data class CartScreenState (
    val isLoading: Boolean = true,
    val products: List<ProductInCart> = emptyList(),
    val showProductDetails: Boolean = false,
    val productDetails: List<String> = emptyList(),
    val address: Address? = null
)
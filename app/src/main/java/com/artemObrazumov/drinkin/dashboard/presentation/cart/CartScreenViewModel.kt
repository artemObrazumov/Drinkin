package com.artemObrazumov.drinkin.dashboard.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.Cart
import com.artemObrazumov.drinkin.dashboard.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetProductsInCartUseCase
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getProductsInCartUseCase: GetProductsInCartUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
): ViewModel() {

    private val _state =
        MutableStateFlow<CartScreenState>(CartScreenState.Content())
    val state = _state
        .onStart { loadCart() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CartScreenState.Content()
        )

    private fun loadCart() {
        viewModelScope.launch {
            addProductsToCartUseCase.invoke(1, 2, mapOf())
            val productsInCart = getProductsInCartUseCase.invoke()
            _state.update {
                CartScreenState.Content(
                    products = productsInCart
                )
            }
        }
    }
}
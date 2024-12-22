package com.artemObrazumov.drinkin.dashboard.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import com.artemObrazumov.drinkin.dashboard.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.cart.DecrementProductInCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.cart.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.cart.GetProductsInCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.cart.IncrementProductInCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.cart.RemoveProductFromCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getProductsInCartFlowUseCase: GetProductsInCartFlowUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val incrementProductInCartUseCase: IncrementProductInCartUseCase,
    private val decrementProductInCartUseCase: DecrementProductInCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CartScreenState())
    val state = _state
        .onStart { loadCart() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CartScreenState()
        )

    private fun loadCart() {
        viewModelScope.launch {
            addProductsToCartUseCase.invoke(1, 2, mapOf())
            getProductsInCartFlowUseCase.invoke().collect { productsInCart ->
                _state.update {
                    state.value.copy(
                        products = productsInCart.toList()
                    )
                }
            }
        }
    }

    fun incrementProduct(productId: Int) {
        viewModelScope.launch {
            incrementProductInCartUseCase.invoke(productId)
        }
    }

    fun decrementProduct(productId: Int) {
        viewModelScope.launch {
            decrementProductInCartUseCase.invoke(productId)
        }
    }

    fun removeProduct(productId: Int) {
        viewModelScope.launch {
            removeProductFromCartUseCase.invoke(productId)
        }
    }

    fun showProductDetails(details: Map<String, String>) {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(
                    showProductDetails = true,
                    productDetails = details.map { entry ->
                        "${entry.key}: ${entry.value}"
                    }
                )
            }
        }
    }

    fun hideProductDetails() {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(
                    showProductDetails = false
                )
            }
        }
    }
}
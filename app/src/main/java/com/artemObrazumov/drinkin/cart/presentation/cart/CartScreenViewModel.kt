package com.artemObrazumov.drinkin.cart.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.cart.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.DecrementProductInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.IncrementProductInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.RemoveProductFromCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getProductsInCartFlowUseCase: GetProductsInCartFlowUseCase,
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
            getProductsInCartFlowUseCase.invoke().collect { productsInCart ->
                _state.update {
                    _state.value.copy(
                        isLoading = false,
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
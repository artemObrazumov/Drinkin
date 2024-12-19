package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.dashboard.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetProductDetailUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetProductDetailUseCaseResult
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductDetailsUi
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import kotlin.math.max

class ProductDetailsViewModel(
    private val productId: Int,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
): ViewModel() {

    private val _state = MutableStateFlow<ProductDetailsScreenState>(ProductDetailsScreenState.Loading)
    val state = _state
        .onStart { loadProductDetails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ProductDetailsScreenState.Loading
        )

    private fun loadProductDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                ProductDetailsScreenState.Loading
            }
            when(val getDetailResult = getProductDetailUseCase.invoke(productId)) {
                is GetProductDetailUseCaseResult.Failure -> {
                    _state.update {
                        ProductDetailsScreenState.Failure(
                            error = getDetailResult.error
                        )
                    }
                }
                is GetProductDetailUseCaseResult.Success -> {
                    _state.update {
                        ProductDetailsScreenState.Content(
                            productDetailsUi = getDetailResult.productDetails.toProductDetailsUi("$"),
                            count = 0,
                            selectedParameters = emptyMap()
                        )
                    }
                }
            }
        }
    }

    fun incrementCount() {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(
                    count = state.count + 1
                )
            }
        }
    }

    fun decrementCount() {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(
                    count = max(0, state.count - 1)
                )
            }
        }
    }

    fun onParameterSelect(parameter: String, optionIndex: Int) {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(
                    selectedParameters = state.selectedParameters.toMutableMap().apply {
                        put(parameter, optionIndex)
                    }
                )
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(addingToCart = true)
            }
        }
    }
}
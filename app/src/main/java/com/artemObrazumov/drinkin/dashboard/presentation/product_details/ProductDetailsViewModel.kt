package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.AddProductsToCartUseCaseResult
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetProductDetailUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetProductDetailUseCaseResult
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductDetailsUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.max

class ProductDetailsViewModel(
    private val productId: Int,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<ProductDetailsScreenState>(ProductDetailsScreenState.Loading)
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
            when (val getDetailResult = getProductDetailUseCase.invoke(productId)) {
                is GetProductDetailUseCaseResult.Failure -> {
                    _state.update {
                        ProductDetailsScreenState.Failure(
                            error = getDetailResult.error
                        )
                    }
                }

                is GetProductDetailUseCaseResult.Success -> {
                    val productDetailsUi = getDetailResult.productDetails
                        .toProductDetailsUi(PRICE_UNIT)
                    _state.update {
                        ProductDetailsScreenState.Content(
                            productDetailsUi = productDetailsUi,
                            count = 0,
                            selectedParameters = emptyMap()
                        )
                    }
                }
            }
        }
    }

    private fun updatePrice(newPrice: Float) {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(
                    productDetailsUi = state.productDetailsUi.copy(
                        basePrice = newPrice.asFormattedPrice(PRICE_UNIT)
                    )
                )
            }
        }
    }

    fun incrementCount() {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content
            _state.update {
                state.copy(
                    buttonState = ButtonState.Idle,
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
                    buttonState = ButtonState.Idle,
                    count = max(0, state.count - 1)
                )
            }
        }
    }

    fun onParameterSelect(parameter: String, optionIndex: Int) {
        viewModelScope.launch {
            val state = _state.value as ProductDetailsScreenState.Content

            val currentParameterIndex = state.selectedParameters[parameter]
            if (currentParameterIndex != null && currentParameterIndex == optionIndex) {
                return@launch
            }

            val currentParameterPriceDifference = currentParameterIndex?.let { index ->
                state.productDetailsUi.customizableParams
                    .first { it.name == parameter }.options[index].priceDifference.value
            } ?: run { 0f }

            val newParameterPriceDifference = state.productDetailsUi.customizableParams
                .first { it.name == parameter }.options[optionIndex].priceDifference.value
            _state.update {
                state.copy(
                    selectedParameters = state.selectedParameters.toMutableMap().apply {
                        put(parameter, optionIndex)
                    }
                )
            }

            val newPrice = state.productDetailsUi.basePrice.value - currentParameterPriceDifference + newParameterPriceDifference
            updatePrice(newPrice)
        }
    }

    fun addToCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as ProductDetailsScreenState.Content

            if (state.count == 0 ||
                state.selectedParameters.size != state.productDetailsUi.customizableParams.size) {
                return@launch
            }

            _state.update {
                state.copy(buttonState = ButtonState.Loading)
            }
            val result = addProductsToCartUseCase.invoke(
                productId = state.productDetailsUi.id,
                count = state.count,
                selectedParameters = state.selectedParameters
            )
            when (result) {
                is AddProductsToCartUseCaseResult.Failure -> {
                    _state.update {
                        state.copy(
                            buttonState = ButtonState.Failure
                        )
                    }
                }

                AddProductsToCartUseCaseResult.Success -> {
                    _state.update {
                        state.copy(
                            buttonState = ButtonState.Success,
                            count = 0
                        )
                    }
                }
            }
        }
    }
}
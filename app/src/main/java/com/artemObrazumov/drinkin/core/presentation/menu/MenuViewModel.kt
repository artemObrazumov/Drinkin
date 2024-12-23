package com.artemObrazumov.drinkin.core.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val getProductsInCartFlowUseCase: GetProductsInCartFlowUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state = _state
        .onStart { subscribeToCartUpdates() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MenuState()
        )

    private fun subscribeToCartUpdates() {
        viewModelScope.launch {
            getProductsInCartFlowUseCase.invoke().collect { products ->
                _state.update {
                    _state.value.copy(
                        basketHasElements = products.isNotEmpty()
                    )
                }
            }
        }
    }
}
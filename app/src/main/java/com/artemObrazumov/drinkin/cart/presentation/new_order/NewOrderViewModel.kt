package com.artemObrazumov.drinkin.cart.presentation.new_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.address.domain.usecase.GetAddressFlowUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetOrderUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetOrderUseCaseResult
import com.artemObrazumov.drinkin.cart.domain.util.AddressError
import com.artemObrazumov.drinkin.cart.presentation.models.toOrderUi
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewOrderViewModel(
    private val getOrderUseCase: GetOrderUseCase,
    private val getAddressFlowUseCase: GetAddressFlowUseCase
): ViewModel() {

    private val _state: MutableStateFlow<NewOrderScreenState> =
        MutableStateFlow(NewOrderScreenState.Loading)
    val state = _state
        .onStart { loadOrderAndAddress() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            NewOrderScreenState.Loading
        )

    private fun loadOrderAndAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            val getOrderResultFlow = flow {
                emit(getOrderUseCase.invoke())
            }
            getOrderResultFlow.combine(
                getAddressFlowUseCase.invoke(),
                transform = { result, address -> Pair(result, address) }
            ).collect {
                val result = it.first
                val address = it.second
                if (address == null) {
                    _state.update {
                        NewOrderScreenState.Failure(AddressError.NO_ADDRESS_ERROR)
                    }
                    return@collect
                }

                when (result) {
                    is GetOrderUseCaseResult.Success -> {
                        _state.update {
                            NewOrderScreenState.Content(
                                order = result.order.toOrderUi(),
                                address = address
                            )
                        }
                    }
                    is GetOrderUseCaseResult.Failure -> {
                        NewOrderScreenState.Failure(result.error)
                    }
                }
            }
        }
    }
}
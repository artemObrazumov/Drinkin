package com.artemObrazumov.drinkin.order.presentation.new_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.address.domain.usecase.GetAddressFlowUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderUseCaseResult
import com.artemObrazumov.drinkin.order.domain.usecase.OrderPaymentResult
import com.artemObrazumov.drinkin.order.domain.usecase.OrderPaymentUseCase
import com.artemObrazumov.drinkin.cart.domain.util.AddressError
import com.artemObrazumov.drinkin.order.presentation.models.toOrderUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewOrderScreenViewModel(
    private val getOrderUseCase: GetOrderUseCase,
    private val getAddressFlowUseCase: GetAddressFlowUseCase,
    private val orderPaymentUseCase: OrderPaymentUseCase
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

    private var orderPaymentState = OrderPaymentState()

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
                                address = address,
                                orderPaymentState = orderPaymentState
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

    fun startPayment() {
        if (state.value !is NewOrderScreenState.Content) return
        viewModelScope.launch {
            orderPaymentState = orderPaymentState.copy(
                loading = true,
                error = null
            )
            val contentState = state.value as NewOrderScreenState.Content
            _state.update {
                contentState.copy(
                    orderPaymentState = orderPaymentState
                )
            }
            val orderId = contentState.order.id
            when(val result = orderPaymentUseCase.invoke(orderId)) {
                is OrderPaymentResult.Failure -> {
                    orderPaymentState = orderPaymentState.copy(error = result.error)
                }

                OrderPaymentResult.Success -> {
                    _state.update {
                        contentState.copy(
                            paymentFinished = true
                        )
                    }
                }
            }
        }
    }
}
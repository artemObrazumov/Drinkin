package com.artemObrazumov.drinkin.order.presentation.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.core.domain.util.CommonError
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderItemsFlowUseCase
import com.artemObrazumov.drinkin.order.presentation.models.toOrderUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsScreenViewModel(
    private val id: Int,
    private val getOrderItemsFlowUseCase: GetOrderItemsFlowUseCase
): ViewModel() {

    private val _state: MutableStateFlow<OrderDetailsScreenState> =
        MutableStateFlow(OrderDetailsScreenState.Loading)
    val state = _state
        .onStart { subscribeToOrderListFlow() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            OrderDetailsScreenState.Loading
        )

    private fun subscribeToOrderListFlow() {
        viewModelScope.launch {
            getOrderItemsFlowUseCase.invoke().collect { orders ->
                orders.firstOrNull()?.let { order ->
                    _state.update {
                        OrderDetailsScreenState.Content(
                            order = order.toOrderUi()
                        )
                    }
                } ?: run {
                    _state.update {
                        OrderDetailsScreenState.Failure(
                            error = CommonError.NOT_FOUND_ERROR
                        )
                    }
                }
            }
        }
    }
}
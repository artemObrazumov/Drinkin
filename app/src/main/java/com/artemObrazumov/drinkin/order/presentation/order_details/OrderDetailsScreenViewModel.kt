package com.artemObrazumov.drinkin.order.presentation.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.core.domain.util.CommonError
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderItemsFlowUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemsListResult
import com.artemObrazumov.drinkin.order.presentation.models.toOrderUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsScreenViewModel(
    private val id: Int,
    private val updateOrderItemUseCase: UpdateOrderItemUseCase,
    private val getOrderItemsFlowUseCase: GetOrderItemsFlowUseCase
): ViewModel() {

    private val _state: MutableStateFlow<OrderDetailsScreenState> =
        MutableStateFlow(OrderDetailsScreenState.Loading)
    val state = _state
        .onStart { updateOrderItem() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            OrderDetailsScreenState.Loading
        )

    private fun updateOrderItem() {
        viewModelScope.launch {
            when(val result = updateOrderItemUseCase.invoke(id)) {
                is UpdateOrderItemsListResult.Failure -> {
                    _state.update {
                        OrderDetailsScreenState.Failure(error = result.error)
                    }
                }
                UpdateOrderItemsListResult.Success -> {
                    subscribeToOrderListFlow()
                }
            }
        }
    }

    private fun subscribeToOrderListFlow() {
        viewModelScope.launch {
            getOrderItemsFlowUseCase.invoke().collect { orders ->
                orders.firstOrNull{ it.id == id }?.let { order ->
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
package com.artemObrazumov.drinkin.order.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.order.domain.models.toOrderItem
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderItemsFlowUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemsListResult
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemsListUseCase
import com.artemObrazumov.drinkin.order.presentation.models.toOrderItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersScreenViewModel(
    private val updateOrderItemsListUseCase: UpdateOrderItemsListUseCase,
    private val getOrderItemsFlowUseCase: GetOrderItemsFlowUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<OrdersScreenState> =
        MutableStateFlow(OrdersScreenState.Loading)
    val state = _state
        .onStart { loadOrders() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            OrdersScreenState.Loading
        )

    private fun loadOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = updateOrderItemsListUseCase.invoke()) {
                UpdateOrderItemsListResult.Success -> {
                    subscribeToOrderItemsFlow()
                }

                is UpdateOrderItemsListResult.Failure -> {
                    _state.update {
                        OrdersScreenState.Failure(error = result.error)
                    }
                }
            }
        }
    }

    private fun subscribeToOrderItemsFlow() {
        viewModelScope.launch {
            getOrderItemsFlowUseCase.invoke()
                .map {
                    it.map { orderItem -> orderItem.toOrderItem().toOrderItemUi() }
                }
                .collect { orderItems ->
                    _state.update {
                        OrdersScreenState.Content(
                            orders = orderItems
                        )
                    }
                }
        }
    }

}
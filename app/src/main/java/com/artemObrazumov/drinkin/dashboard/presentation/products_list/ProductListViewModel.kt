package com.artemObrazumov.drinkin.dashboard.presentation.products_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetDashboardUseCase
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetDashboardUseCaseResult
import com.artemObrazumov.drinkin.dashboard.presentation.models.toCategoryUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getDashboardUseCase: GetDashboardUseCase
): ViewModel() {

    private val _state = MutableStateFlow<ProductListScreenState>(ProductListScreenState.Loading)
    val state = _state
        .onStart { loadDashboard() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ProductListScreenState.Loading
        )

    private fun loadDashboard() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                ProductListScreenState.Loading
            }
            when(val getDashboardResult = getDashboardUseCase.invoke()) {
                is GetDashboardUseCaseResult.Failure -> {
                    _state.update {
                        ProductListScreenState.Failure(
                            error = getDashboardResult.error
                        )
                    }
                }
                is GetDashboardUseCaseResult.Success -> {
                    _state.update {
                        ProductListScreenState.Content(
                            categories = getDashboardResult.categories
                                .map { it.toCategoryUi() },
                            products = getDashboardResult.products
                                .map { it.toProductUi(PRICE_UNIT) }
                        )
                    }
                }
            }
        }
    }
}
package com.artemObrazumov.drinkin.address.presentation.addressSelect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.address.domain.usecase.ChangeAddressUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetAddressFlowUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetCafesUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetCafesUseCaseResult
import com.artemObrazumov.drinkin.address.presentation.models.CafeUi
import com.artemObrazumov.drinkin.address.presentation.models.toCafeUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddressSelectViewModel(
    private val getAddressFlowUseCase: GetAddressFlowUseCase,
    private val getCafesUseCase: GetCafesUseCase,
    private val changeAddressUseCase: ChangeAddressUseCase
): ViewModel() {

    private val _state = MutableStateFlow<AddressSelectScreenState>(AddressSelectScreenState.Loading)
    val state = _state
        .onStart { getCafes() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            AddressSelectScreenState.Loading
        )


    private fun getCafes() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getCafesUseCase.invoke()) {
                is GetCafesUseCaseResult.Failure -> {

                }
                is GetCafesUseCaseResult.Success -> {
                    _state.update {
                        AddressSelectScreenState.Content(
                            cafes = result.cafes.map {
                                it.toCafeUi()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun subscribeToAddressUpdates() {
        viewModelScope.launch {
            getAddressFlowUseCase.invoke().collect{ address ->

            }
        }
    }

    fun selectCafe(cafe: CafeUi) {
        viewModelScope.launch {
            val state = _state.value as AddressSelectScreenState.Content
            _state.update {
                state.copy(
                    isCafeOpened = true,
                    selectedCafe = cafe
                )
            }
        }
    }
}
package com.artemObrazumov.drinkin.core.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.account.domain.usecase.GetUserFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getUserFlowUseCase: GetUserFlowUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AccountState> = MutableStateFlow(AccountState.Loading)
    val state = _state
        .onStart { subscribeToUserFlow() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AccountState.Loading
        )

    private fun subscribeToUserFlow() {
        viewModelScope.launch {
            getUserFlowUseCase().collect { user ->
                _state.update { AccountState.Content(user) }
            }
        }
    }
}
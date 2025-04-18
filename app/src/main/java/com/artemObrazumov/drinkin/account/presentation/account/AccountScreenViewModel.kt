package com.artemObrazumov.drinkin.account.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.account.domain.usecase.GetUserFlowUseCase
import com.artemObrazumov.drinkin.core.domain.util.AuthorizationError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountScreenViewModel(
    private val getUserFlowUseCase: GetUserFlowUseCase
): ViewModel() {

    private val _state = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val state = _state
        .onStart { subscribeToUserFlow() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AccountScreenState.Loading
        )

    private fun subscribeToUserFlow() {
        viewModelScope.launch {
            getUserFlowUseCase().collect { user ->
                _state.update {
                    if (user == null) AccountScreenState.Failure(
                        error = AuthorizationError.NO_USER_FOUND
                    )
                    else AccountScreenState.Content(user)
                }
            }
        }
    }
}
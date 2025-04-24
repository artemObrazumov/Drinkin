package com.artemObrazumov.drinkin.account.presentation.account

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.account.domain.usecase.GetUserFlowUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.LogoutUseCase
import com.artemObrazumov.drinkin.core.domain.util.AuthorizationError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountScreenViewModel(
    private val getUserFlowUseCase: GetUserFlowUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _state = MutableStateFlow(AccountScreenState())
    val state = _state
        .onStart { subscribeToUserFlow() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AccountScreenState()
        )

    private fun subscribeToUserFlow() {
        viewModelScope.launch {
            getUserFlowUseCase().collect { user ->
                val userState = if (user == null) UserState.Failure(
                    error = AuthorizationError.NO_USER_FOUND
                ) else UserState.Content(user)

                _state.update {
                    _state.value.copy(userState = userState)
                }
            }
        }
    }

    fun openLogoutDialog() {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(isLogoutDialogOpened = true)
            }
        }
    }

    fun doLogout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }

    fun closeLogoutDialog() {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(isLogoutDialogOpened = false)
            }
        }
    }
}
package com.artemObrazumov.drinkin.startup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.account.domain.usecase.AuthorizeUserUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.AuthorizeUserUseCaseResult
import com.artemObrazumov.drinkin.account.domain.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class StartupScreenViewModel(
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
    private val authorizeUserUseCase: AuthorizeUserUseCase
): ViewModel() {

    private val _intents = MutableSharedFlow<StartupScreenIntent>()
    val intents = _intents
        .onStart {checkOnboardingAndAuthorizationStatus() }
        .shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    private fun checkOnboardingAndAuthorizationStatus() {
        viewModelScope.launch {
            // TODO: onboarding check

            val tokens = getTokenFlowUseCase().first()
            if (tokens == null) {
                _intents.emit(StartupScreenIntent.Authorization)
            } else {
                when(authorizeUserUseCase.invoke()) {
                    is AuthorizeUserUseCaseResult.Failure -> {
                        _intents.emit(StartupScreenIntent.Authorization)
                    }
                    AuthorizeUserUseCaseResult.Success -> {
                        _intents.emit(StartupScreenIntent.Dashboard)
                    }
                }
            }
        }
    }
}
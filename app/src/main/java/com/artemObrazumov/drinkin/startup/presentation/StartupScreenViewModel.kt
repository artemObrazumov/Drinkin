package com.artemObrazumov.drinkin.startup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.account.domain.usecase.AuthorizeUserUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.AuthorizeUserUseCaseResult
import com.artemObrazumov.drinkin.account.domain.usecase.GetTokenFlowUseCase
import com.artemObrazumov.drinkin.onboarding.domain.usecase.CheckIfOnboardingSeenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class StartupScreenViewModel(
    private val checkIfOnboardingSeenUseCase: CheckIfOnboardingSeenUseCase,
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
            if (!checkIfOnboardingSeenUseCase()) {
                _intents.emit(StartupScreenIntent.Onboarding)
                return@launch
            }

            val tokens = getTokenFlowUseCase().first()
            if (tokens == null) {
                _intents.emit(StartupScreenIntent.Authorization)
            } else {
                when(authorizeUserUseCase()) {
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
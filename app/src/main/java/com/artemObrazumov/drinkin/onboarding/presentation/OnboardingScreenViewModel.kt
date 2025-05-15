package com.artemObrazumov.drinkin.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.onboarding.domain.usecase.SetOnboardingAsSeenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingScreenViewModel(
    private val setOnboardingAsSeenUseCase: SetOnboardingAsSeenUseCase
): ViewModel() {

    private val _state = MutableStateFlow(OnboardingScreenState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OnboardingScreenState()
        )

    fun finishOnboarding() {
        viewModelScope.launch {
            setOnboardingAsSeenUseCase()
            _state.update {
                _state.value.copy(finishedOnboarding = true)
            }
        }
    }
}
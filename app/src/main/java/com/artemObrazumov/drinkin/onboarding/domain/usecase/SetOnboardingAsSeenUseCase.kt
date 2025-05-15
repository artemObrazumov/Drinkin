package com.artemObrazumov.drinkin.onboarding.domain.usecase

import com.artemObrazumov.drinkin.onboarding.domain.data_source.OnboardingLocalDataSource

class SetOnboardingAsSeenUseCase(
    private val onboardingLocalDataSource: OnboardingLocalDataSource
) {

    suspend operator fun invoke() {
        onboardingLocalDataSource.setAsSeen()
    }
}
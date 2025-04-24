package com.artemObrazumov.drinkin.onboarding.domain.usecase

import com.artemObrazumov.drinkin.onboarding.domain.data_source.OnboardingLocalDataSource

class CheckIfOnboardingSeenUseCase(
    private val onboardingLocalDataSource: OnboardingLocalDataSource
) {

    suspend operator fun invoke(): Boolean {
        return onboardingLocalDataSource.checkIfSeen()
    }
}
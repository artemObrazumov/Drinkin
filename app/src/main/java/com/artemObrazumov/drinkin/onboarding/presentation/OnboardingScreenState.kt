package com.artemObrazumov.drinkin.onboarding.presentation

data class OnboardingScreenState(
    val page: Int = 0,
    val totalPages: Int = OnboardingContent.pages.size,
    val finishedOnboarding: Boolean = false
)
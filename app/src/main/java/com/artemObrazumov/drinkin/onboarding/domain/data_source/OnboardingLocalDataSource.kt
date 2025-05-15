package com.artemObrazumov.drinkin.onboarding.domain.data_source

interface OnboardingLocalDataSource {

    suspend fun checkIfSeen(): Boolean
    suspend fun setAsSeen()
}
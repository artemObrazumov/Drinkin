package com.artemObrazumov.drinkin.onboarding.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.artemObrazumov.drinkin.onboarding.domain.data_source.OnboardingLocalDataSource
import com.artemObrazumov.drinkin.onboarding.presentation.utils.TokenLocalStorageConstants.SEEN_ONBOARDING
import com.artemObrazumov.drinkin.onboarding.presentation.utils.onboardingDataStore
import kotlinx.coroutines.flow.first

class OnboardingLocalDataSourceImpl(
    private val context: Context
): OnboardingLocalDataSource {

    override suspend fun checkIfSeen(): Boolean {
        val preferences = context.onboardingDataStore.data.first()
        return preferences[SEEN_ONBOARDING]?: false
    }

    override suspend fun setAsSeen() {
        context.onboardingDataStore.edit { preferences ->
            preferences[SEEN_ONBOARDING] = true
        }
    }
}
package com.artemObrazumov.drinkin.onboarding.presentation.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = TokenLocalStorageConstants.STORAGE_NAME
)

object TokenLocalStorageConstants {

    const val STORAGE_NAME = "onboarding"
    val SEEN_ONBOARDING = booleanPreferencesKey("seen_onboarding")
}
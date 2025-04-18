package com.artemObrazumov.drinkin.account.presentation.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
    name = TokenLocalStorageConstants.STORAGE_NAME
)

object TokenLocalStorageConstants {

    const val STORAGE_NAME = "tokens"
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}
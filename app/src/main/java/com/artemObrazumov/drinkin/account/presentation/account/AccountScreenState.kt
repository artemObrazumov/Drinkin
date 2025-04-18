package com.artemObrazumov.drinkin.account.presentation.account

import com.artemObrazumov.drinkin.account.domain.models.User
import com.artemObrazumov.drinkin.core.domain.util.Error

sealed class AccountScreenState {

    data object Loading: AccountScreenState()
    data class Content(
        val user: User
    ): AccountScreenState()
    data class Failure(
        val error: Error
    ): AccountScreenState()
}
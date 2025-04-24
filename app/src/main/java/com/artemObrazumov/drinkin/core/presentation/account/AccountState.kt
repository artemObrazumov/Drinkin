package com.artemObrazumov.drinkin.core.presentation.account

import com.artemObrazumov.drinkin.account.domain.models.User

sealed class AccountState {

    data object Loading: AccountState()
    data class Content(val user: User? = null): AccountState()
}
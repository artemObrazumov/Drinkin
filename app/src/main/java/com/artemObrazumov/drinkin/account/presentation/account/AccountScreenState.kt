package com.artemObrazumov.drinkin.account.presentation.account

import com.artemObrazumov.drinkin.account.domain.models.User
import com.artemObrazumov.drinkin.core.domain.util.Error

data class AccountScreenState(
    val isLogoutDialogOpened: Boolean = false,
    val userState: UserState = UserState.Loading
)

sealed class UserState {

    data object Loading: UserState()
    data class Content(
        val user: User
    ): UserState()
    data class Failure(
        val error: Error
    ): UserState()
}
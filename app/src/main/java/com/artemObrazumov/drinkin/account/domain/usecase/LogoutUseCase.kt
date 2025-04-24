package com.artemObrazumov.drinkin.account.domain.usecase

import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.data_source.UserDataSource

class LogoutUseCase(
    private val loginDataSource: LoginDataSource,
    private val userDataSource: UserDataSource
) {

    suspend operator fun invoke() {
        loginDataSource.logout()
        userDataSource.logoutUser()
    }
}